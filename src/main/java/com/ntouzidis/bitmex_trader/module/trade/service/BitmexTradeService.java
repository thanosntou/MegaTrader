package com.ntouzidis.bitmex_trader.module.trade.service;

import com.ntouzidis.bitmex_trader.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.enumeration.OrderType;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.common.pojo.Context;
import com.ntouzidis.bitmex_trader.module.common.pojo.OrderReport;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Predicate;

import static com.ntouzidis.bitmex_trader.CooperativeApplication.logger;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.CLOSE;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.WALLET_BALANCE;

@Service
@RequiredArgsConstructor
public class BitmexTradeService implements IBitmexTradeService {

  private final Context context;
  private final UserService userService;
  private final IBitmexApiService bitmexApiService;
  private final ExecutorService multiExecutor;

  @Override
  public Map<String, Double> getFollowerBalances(Long traderId) {
    User trader = userService.getTrader(traderId);

    Map<String, Double> map = new HashMap<>();

    userService.getEnabledFollowers(trader).forEach(user -> {
      try {
        double userBalance = ((Integer) bitmexApiService.getUserMargin(user).get(WALLET_BALANCE)).doubleValue() / 100000000;
        map.put(user.getUsername(), userBalance);
      } catch (Exception e) {
        logger.warn(String.format("failed to get read balance of follower: %s", user.getUsername()));
      }
    });
    return map;
  }

  @Override
  public Map<String, Double> getFollowerBalances(String traderName) {
    User trader = userService.getTrader(traderName);

    Map<String, Double> map = new HashMap<>();

    userService.getEnabledFollowers(trader).forEach(user -> {
      try {
        double userBalance = ((Integer) bitmexApiService.getUserMargin(user).get(WALLET_BALANCE)).doubleValue() / 100000000;
        map.put(user.getUsername(), userBalance);
      } catch (Exception e) {
        logger.warn(String.format("failed to get read balance of follower: %s", user.getUsername()));
      }
    });
    return map;
  }

  @Override
  public OrderReport placeOrderForAll(User trader, int percentage, DataOrderBuilder dataOrder, DataLeverageBuilder dataLeverage) {
    final List<User> enabledFollowers = Collections.synchronizedList(new ArrayList<>(userService.getEnabledFollowers(trader)));

    dataOrder.withClOrdId(UUID.randomUUID().toString());

    setLeverages(enabledFollowers, dataLeverage);

    final Predicate<User> orderFunction = getOrderFunction(dataOrder, dataLeverage, percentage);

    List<Future<Boolean>> futureList = executeOrders(enabledFollowers, orderFunction);

    return generateOrderReport(futureList);
  }

  @Override
  public OrderReport postOrderWithPercentage(User trader, int percentage, DataOrderBuilder dataOrder) {
    final String uniqueclOrdID = UUID.randomUUID().toString();
    final List<Future<Boolean>> futureList = Collections.synchronizedList(new ArrayList<>());
    final Map<Long, DataOrderBuilder> orderBuilderMap = Collections.synchronizedMap(new HashMap<>());

    List<User> enabledfollowers = userService.getEnabledFollowers(trader);

    enabledfollowers.forEach(follower -> orderBuilderMap.put(follower.getId(),  SerializationUtils.clone(dataOrder)));

    enabledfollowers.parallelStream().forEach(follower -> futureList.add(
        multiExecutor.submit(() -> futureList.add(multiExecutor.submit(() -> {
          try {
            dataOrder
                .withClOrdId(uniqueclOrdID)
                .withOrderQty(calculateQtyFromOpenPosition(follower, dataOrder.getSymbol(), percentage));

            bitmexApiService.postOrderOrder(follower, dataOrder);
            return true;
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
          }
        })))
    ));
    waitFuturesToComplete(futureList);
    return generateOrderReport(futureList);
  }

  private void setLeverages(List<User> enabledFollowers, DataLeverageBuilder dataLeverage) {
    final List<User> failedFollowers = Collections.synchronizedList(new ArrayList<>());
    final List<Future<Boolean>> futureList = Collections.synchronizedList(new ArrayList<>());

    enabledFollowers.parallelStream().forEach(follower -> {
      try {
        futureList.add(multiExecutor.submit(() -> {
          try {
            bitmexApiService.postPositionLeverage(follower, dataLeverage);
            return true;
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
          }
        }));
      } catch (NullPointerException | RejectedExecutionException | CancellationException e) {
        logger.error(e.getMessage(), e.getCause());
        failedFollowers.add(follower);
      }
    });
    waitFuturesToComplete(futureList);

    if (!failedFollowers.isEmpty()) {
      logger.warn("Leverage setting failed for [{}] followers", failedFollowers.size());
      enabledFollowers.removeAll(failedFollowers);
    }
  }

  private Predicate<User> getOrderFunction(DataOrderBuilder dataOrder, DataLeverageBuilder dataLeverage, Integer percentage) {
    final String leverage = dataLeverage.getLeverage();
    final OrderType orderType = dataOrder.getOrderType();
    switch (orderType) {
      case Stop:
      case StopLimit:
        return getStopOrStopLimitFunction(percentage, dataOrder);
      case Limit:
        return getLimitFunction(percentage, dataOrder, leverage);
      case Market:
        return getMarketFunction(percentage, dataOrder, leverage);
      default:
        throw new RuntimeException("OrderType not specified");
    }
  }

  private Predicate<User> getStopOrStopLimitFunction(Integer percentage, DataOrderBuilder commonOrderData) {
    return follower -> {
      try {
        String orderQty = "0".equals(commonOrderData.getOrderQty()) ?
            findActiveOrder(follower, commonOrderData.getSymbol())
                .orElseThrow(RuntimeException::new)
                .getOrderQty()
            : calculateQtyFromOpenPosition(follower, commonOrderData.getSymbol(), percentage);

        executeOrder(follower, commonOrderData, orderQty);
        return true;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        return false;
      }
    };
  }

  private Predicate<User> getLimitFunction(Integer percentage, DataOrderBuilder commonOrderData, String leverage) {
    return follower -> {
      try {
        String orderQty = CLOSE.equals(commonOrderData.getExecInst()) ?
            calculateQtyFromOpenPosition(follower, commonOrderData.getSymbol(), percentage)
            : calculateFixedQtyForSymbol(follower, commonOrderData.getSymbol(), leverage, commonOrderData.getPrice(), percentage);

        executeOrder(follower, commonOrderData, orderQty);
        return true;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        return false;
      }
    };
  }

  private Predicate<User> getMarketFunction(Integer percentage, DataOrderBuilder commonOrderData, String leverage) {
    return follower -> {
      try {
        String instPrice = bitmexApiService.getInstrumentLastPrice(follower, commonOrderData.getSymbol());
        String orderQty = calculateFixedQtyForSymbol(follower, commonOrderData.getSymbol(), leverage, instPrice, percentage);
        executeOrder(follower, commonOrderData, orderQty);
        return true;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        return false;
      }
    };
  }

  private void executeOrder(User user, DataOrderBuilder orderData, String orderQty) {
    bitmexApiService.postOrderOrder(user, SerializationUtils.clone(orderData).withOrderQty(orderQty));
  }

  private List<Future<Boolean>> executeOrders(List<User> followers, Predicate<User> orderFunction) {
    final List<Future<Boolean>> futureList = Collections.synchronizedList(new ArrayList<>());
    followers.parallelStream().forEach(follower -> futureList.add(multiExecutor.submit(() -> orderFunction.test(follower))));
    waitFuturesToComplete(futureList);
    return futureList;
  }

  @Override
  public List<BitmexOrder> getGuideActiveOrders(User trader) {
    return bitmexApiService.getActiveOrders(userService.getGuideFollower(trader));
  }

  @Override
  public List<BitmexPosition> getGuideOpenPositions(User trader) {
    return bitmexApiService.getOpenPositions(userService.getGuideFollower(trader));
  }

  @Override
  public Map<String, Double> getBalances() {
    Map<String, Double> allBalances = getFollowerBalances(context.getUserID());
    Map<String, Double> followerBalances = new HashMap<>();

    userService.getNonHiddenFollowers(context.getUser()).forEach(follower ->
        followerBalances.put(follower.getUsername(), allBalances.get(follower.getUsername())));

    return followerBalances;
  }

  @Override
  public void cancelOrder(User trader, DataDeleteOrderBuilder dataDeleteOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexApiService.cancelOrder(follower, dataDeleteOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
    });
  }

  @Override
  public void cancelAllOrders(User trader, DataDeleteOrderBuilder dataDeleteOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexApiService.cancelAllOrders(follower, dataDeleteOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
    });
  }

  @Override
  public void closeAllPosition(User trader, DataOrderBuilder dataOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexApiService.postOrderOrder(follower, dataOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
    });
  }

  @Override
  public void panicButton(User trader) {
    DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder();
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder();

    for (Symbol symbol: Symbol.values()) {
      cancelAllOrders(trader, dataDeleteOrderBuilder.withSymbol(symbol));

      dataOrderBuilder.withSymbol(symbol)
              .withOrderType(OrderType.Market)
              .withExecInst(CLOSE);

      closeAllPosition(trader, dataOrderBuilder);
    }
  }

  Map<String, Integer> calculateSumFixedQtys(List<User> followers) {
    Map<String, Integer> sumFixedQtys = new HashMap<>();
    Arrays.stream(Symbol.values()).forEach(symbol -> sumFixedQtys.put(symbol.name(), 0));

    followers
        .forEach(follower -> Arrays.stream(Symbol.values())
            .forEach(symbol -> sumFixedQtys.put(symbol.name(),
                sumFixedQtys.get(symbol.name()) + follower.getQtyPreference(symbol).getValue())));

    return sumFixedQtys;
  }

  Map<String, Double> calculateSumPositions(List<User> followers) {
    Map<String, Double> sumPositions = new HashMap<>();
    Arrays.stream(Symbol.values()).forEach(symbol -> sumPositions.put(symbol.name(), (double) 0));

    try {
      followers.forEach(follower -> bitmexApiService.getOpenPositions(follower)
          .stream()
          .filter(Objects::nonNull)
          .forEach(map -> {
            String sym = map.getSymbol().name();
            sumPositions.put(sym, sumPositions.get(sym) + Double.parseDouble(map.getSize().toString()));
          })
      );
    } catch (Exception e){
      logger.error(e.getMessage(), e.getCause());
    }
    return sumPositions;
  }

  private Optional<BitmexOrder> findActiveOrder(User user, Symbol symbol) {
    return bitmexApiService.getActiveOrders(user)
        .stream()
        .filter(i -> symbol.name().equals(i.getSymbol()))
        .findFirst();
  }

  private String calculateQtyFromOpenPosition(User user, Symbol symbol, Integer percentage) {
    BitmexPosition position = bitmexApiService.getSymbolPosition(user, symbol);
    return String.valueOf(Math.abs(position.getSize() * percentage / 100));
  }

  private String calculateFixedQtyForSymbol(User user, Symbol symbol, String leverage, String lastPrice, Integer percentage) {
    if (symbol.equals(Symbol.XBTUSD)) return calculateOrderQtyXBTUSD(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.ETHUSD)) return calculateOrderQtyETHUSD(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.ADAXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.BCHXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.EOSXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.ETHXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.LTCXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.TRXXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.XRPXXX)) return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    throw new RuntimeException("Fixed qty user calculation failed");
  }

  private String calculateOrderQtyXBTUSD(User user, Integer percentage, String lev, String lastPrice) {
    return String.valueOf(Math.round(xbtAmount(user, percentage) * leverage(lev) * lastPrice(lastPrice)));
  }

  private String calculateOrderQtyOther(User user, Integer percentage, String lev, String lastPrice) {
    return String.valueOf(Math.round(xbtAmount(user, percentage) * leverage(lev) / lastPrice(lastPrice)));
  }


  private String calculateOrderQtyETHUSD(User user, Integer percentage, String lev, String lastPrice) {
    return String.valueOf(Math.round((xbtAmount(user, percentage) * leverage(lev)) / (lastPrice(lastPrice) * 0.000001)));
  }

  private Double xbtAmount(User user, Integer percentage) {
    return (((double)percentage) / 100) * (((Integer) bitmexApiService.getUserMargin(user).get(WALLET_BALANCE)).doubleValue() / 100000000);
  }

  private Double leverage(String leverage) {
    return Double.parseDouble(leverage);
  }

  private Double lastPrice(String lastPrice) {
    return Double.parseDouble(lastPrice);
  }

  private void waitFuturesToComplete(List<Future<Boolean>> futureList) {
    futureList.forEach(this::waitFutureToComplete);
  }

  private void waitFutureToComplete(Future<?> future) {
    try {
      future.get();
    } catch (InterruptedException | CancellationException | ExecutionException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private OrderReport generateOrderReport(List<Future<Boolean>> futureList) {
    final OrderReport report = new OrderReport();

    futureList.forEach(future -> {
      boolean futureResult = false;
      try {
        futureResult = future.get();
      } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
      if (futureResult)
        report.addOneSucceeded();
      else
        report.addOneFailed();
    });
    return report;
  }


}
