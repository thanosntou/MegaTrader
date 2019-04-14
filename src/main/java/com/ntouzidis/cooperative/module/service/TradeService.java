package com.ntouzidis.cooperative.module.service;

import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataOrderBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.pojo.OrderReport;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class TradeService {

  private Logger logger = LoggerFactory.getLogger(TradeService.class);

  private final UserService userService;
  private final BitmexService bitmexService;
  private final ExecutorService multiExecutor;

  public TradeService(UserService userService,
                      BitmexService bitmexService,
                      ExecutorService multiExecutor) {

    this.userService = userService;
    this.bitmexService = bitmexService;
    this.multiExecutor = multiExecutor;
  }

  public OrderReport placeOrderAll(User trader, DataLeverageBuilder dataLeverage, DataOrderBuilder dataOrder, Integer percentage) {
    final Future<?>[] future = new Future[1];
    final OrderReport report = new OrderReport();

    List<User> enabledFollowers = userService.getEnabledFollowers(trader);

    setLeverage(enabledFollowers, future, dataLeverage);
    waitToComplete(future[0]);

    configureAndPlaceOrder(enabledFollowers, future, dataOrder, dataLeverage, percentage, report);
    waitToComplete(future[0]);

    return report;
  }

  private void configureAndPlaceOrder(List<User> followers, Future<?>[] future, DataOrderBuilder dataOrder,
                                      DataLeverageBuilder dataLeverage, Integer percentage, OrderReport report) {
    final String uniqueClOrdID = UUID.randomUUID().toString();
    followers.forEach(follower -> future[0] = multiExecutor.submit(() -> {
      try {
        // OPTION 1: STOP or STOP LIMIT
        if (OrderType.Stop.equals(dataOrder.getOrderType()) || OrderType.StopLimit.equals(dataOrder.getOrderType())) {
          dataOrder.withOrderQty(calculateQtyFromOpenPosition(follower, dataOrder.getSymbol(), percentage));

          if ("0".equals(dataOrder.getOrderQty()))
            dataOrder.withOrderQty(
                findActiveOrder(follower, dataOrder.getSymbol())
                    .orElseThrow(RuntimeException::new)
                    .getOrderQty()
            );
        }
        // OPTION 2: LIMIT
        else if (OrderType.Limit.equals(dataOrder.getOrderType())) {
          dataOrder.withOrderQty(
              "Close".equals(dataOrder.getExecInst()) ?
                  calculateQtyFromOpenPosition(follower, dataOrder.getSymbol(), percentage)
                  : calculateFixedQtyForSymbol(follower, dataOrder.getSymbol(), dataLeverage.getLeverage(), dataOrder.getPrice(), percentage)
          );
        }
        // OPTION 3: MARKET
        else if (OrderType.Market.equals(dataOrder.getOrderType())) {
          dataOrder.withOrderQty(
              calculateFixedQtyForSymbol(
                  follower, dataOrder.getSymbol(), dataLeverage.getLeverage(),
                  bitmexService.getInstrumentLastPrice(follower, dataOrder.getSymbol()), percentage));
        }
        bitmexService.postOrderOrder(follower, dataOrder.withClOrdId(uniqueClOrdID));
        synchronized (this) {
          report.addOneSucceeded();
        }
      } catch (Exception e) {
        synchronized (this) {
          report.addOneFailed();
        }
        logger.error(e.getMessage(), e);
      }
    }));
  }

  private List<User> setLeverage(List<User> users, Future<?>[] future, DataLeverageBuilder dataLeverage) {
    users.removeIf(user -> {
      try {
        future[0] = multiExecutor.submit(() -> bitmexService.postPositionLeverage(user, dataLeverage));
        return false;
      }
      catch (NullPointerException | RejectedExecutionException | CancellationException e) {
        logger.error(e.getMessage(), e.getCause());
        return true;
      }
    });
    return users;
  }

  public void postOrderWithPercentage(User trader, DataOrderBuilder dataOrder, int percentage) {
    final Future<?>[] future = new Future[1];
    final String uniqueclOrdID = UUID.randomUUID().toString();
    List<User> enabledfollowers = userService.getEnabledFollowers(trader);

    for (User follower: enabledfollowers) {
      future[0] = multiExecutor.submit(() -> {
        try {
          dataOrder
              .withClOrdId(uniqueclOrdID)
              .withOrderQty(calculateQtyFromOpenPosition(follower, dataOrder.getSymbol(), percentage));

          bitmexService.postOrderOrder(follower, dataOrder);

        } catch (Exception e) {
          logger.error(e.getMessage(), e);
        }
      });
    }
    waitToComplete(future[0]);
  }

  public List<BitmexOrder> getGuideActiveOrders(User trader) {
    return getActiveOrdersOf(userService.getGuideFollower(trader));
  }

  private List<BitmexOrder> getActiveOrdersOf(User user) {
    return bitmexService.getActiveOrders(user);
  }

  public List<BitmexPosition> getGuideOpenPositions(User trader) {
    return bitmexService.getOpenPositions(userService.getGuideFollower(trader));
  }

  public void cancelOrder(User trader, DataDeleteOrderBuilder dataDeleteOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexService.cancelOrder(follower, dataDeleteOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
    });
  }

  public void cancelAllOrders(User trader, DataDeleteOrderBuilder dataDeleteOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexService.cancelAllOrders(follower, dataDeleteOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
    });
  }

  public void closeAllPosition(User trader, DataOrderBuilder dataOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexService.postOrderOrder(follower, dataOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e);
      }
    });
  }

  public void panicButton(User trader) {
    DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder();
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder();

    for (Symbol symbol: Symbol.values()) {
      cancelAllOrders(trader, dataDeleteOrderBuilder.withSymbol(symbol));

      dataOrderBuilder.withSymbol(symbol)
              .withOrderType(OrderType.Market)
              .withExecInst("Close");

      closeAllPosition(trader, dataOrderBuilder);
    }
  }

  Map<String, Double> calculateSumFixedQtys(List<User> followers) {
    Map<String, Double> sumFixedQtys = new HashMap<>();
    Arrays.stream(Symbol.values()).forEach(symbol -> sumFixedQtys.put(symbol.name(), (double) 0));

    followers.forEach(f -> {
      sumFixedQtys.put(Symbol.XBTUSD.name(), sumFixedQtys.get(Symbol.XBTUSD.name()) + f.getFixedQtyXBTUSD());
      sumFixedQtys.put(Symbol.ETHUSD.name(), sumFixedQtys.get(Symbol.ETHUSD.name()) + f.getFixedQtyXBTJPY());
      sumFixedQtys.put(Symbol.ADAM19.name(), sumFixedQtys.get(Symbol.ADAM19.name()) + f.getFixedQtyADAZ18());
      sumFixedQtys.put(Symbol.BCHM19.name(), sumFixedQtys.get(Symbol.BCHM19.name()) + f.getFixedQtyBCHZ18());
      sumFixedQtys.put(Symbol.EOSM19.name(), sumFixedQtys.get(Symbol.EOSM19.name()) + f.getFixedQtyEOSZ18());
      sumFixedQtys.put(Symbol.ETHM19.name(), sumFixedQtys.get(Symbol.ETHM19.name()) + f.getFixedQtyETHUSD());
      sumFixedQtys.put(Symbol.LTCM19.name(), sumFixedQtys.get(Symbol.LTCM19.name()) + f.getFixedQtyLTCZ18());
      sumFixedQtys.put(Symbol.TRXM19.name(), sumFixedQtys.get(Symbol.TRXM19.name()) + f.getFixedQtyTRXZ18());
      sumFixedQtys.put(Symbol.XRPM19.name(), sumFixedQtys.get(Symbol.XRPM19.name()) + f.getFixedQtyXRPZ18());
    });

    return sumFixedQtys;
  }

  Map<String, Double> calculateSumPositions(List<User> followers) {
    Map<String, Double> sumPositions = new HashMap<>();
    Arrays.stream(Symbol.values()).forEach(symbol -> sumPositions.put(symbol.name(), (double) 0));

    try {
      followers.forEach(follower -> bitmexService.getOpenPositions(follower)
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
    return bitmexService.getActiveOrders(user)
        .stream()
        .filter(i -> symbol.name().equals(i.getSymbol()))
        .findFirst();
  }

  private String calculateQtyFromOpenPosition(User user, Symbol symbol, Integer percentage) {
    BitmexPosition position = bitmexService.getSymbolPosition(user, symbol);
    return String.valueOf(Math.abs(position.getSize() * percentage / 100));
  }

  private String calculateFixedQtyForSymbol(User user, Symbol symbol, String leverage, String lastPrice, Integer percentage) {
    if (symbol.equals(Symbol.XBTUSD))
      return calculateOrderQtyXBTUSD(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.ETHUSD))
      return calculateOrderQtyETHUSD(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.ADAM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.BCHM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.EOSM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.ETHM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.LTCM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.TRXM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);
    if (symbol.equals(Symbol.XRPM19))
      return calculateOrderQtyOther(user, percentage, leverage, lastPrice);

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
    return (((double)percentage) / 100)
        * (((Integer) bitmexService.getUserMargin(user).get("walletBalance")).doubleValue() / 100000000);
  }

  private Double leverage(String leverage) {
    return Double.parseDouble(leverage);
  }

  private Double lastPrice(String lastPrice) {
    return Double.parseDouble(lastPrice);
  }

  private void waitToComplete(Future<?> future) {
    Optional.ofNullable(future).ifPresent(f -> {
      try { future.get(); }
      catch (InterruptedException | CancellationException | ExecutionException e) {
        logger.error(e.getMessage(), e);
        throw new RuntimeException(e.getMessage(), e.getCause());
      }
    });
  }

}
