package com.ntouzidis.cooperative.module.service;

import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataOrderBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class TradeService {

  private Logger logger = LoggerFactory.getLogger(TradeService.class);

  private final UserService userService;
  private final BitmexService bitmexService;
  private final ExecutorService multiExecutor;

  public TradeService(UserService userService, BitmexService bitmexService, ExecutorService multiExecutor) {
    this.userService = userService;
    this.bitmexService = bitmexService;
    this.multiExecutor = multiExecutor;
  }

  public void placeOrderAll(User trader, DataLeverageBuilder dataLeverage, DataOrderBuilder dataOrder) {
    Future<?> future = null;
    List<User> enabledfollowers = userService.getEnabledFollowers(trader);
    final String uniqueclOrdID = UUID.randomUUID().toString();

    for (User follower : enabledfollowers) {
      try {
        future = multiExecutor.submit(() -> bitmexService.post_Position_Leverage(follower, dataLeverage));
      } catch (NullPointerException | RejectedExecutionException | CancellationException e) {
        logger.error(e.getMessage(), e.getCause());
      }
    }

    if (Optional.ofNullable(future).isPresent()) {
      try {
        future.get();
      } catch (InterruptedException | CancellationException | ExecutionException e) {
        logger.error(e.getMessage(), e.getCause());
      }
      if (future.isDone()) {
        for (User follower : enabledfollowers) {
          future = multiExecutor.submit(() -> {
            try {
              if (OrderType.Stop.equals(dataOrder.getOrderType()) || OrderType.StopLimit.equals(dataOrder.getOrderType())) {
                dataOrder.withOrderQty(
                        String.valueOf(Math.abs((Integer)
                                bitmexService.getSymbolPosition(follower, dataLeverage.getSymbol()).get("currentQty"))
                        )
                );
                if ("0".equals(dataOrder.getOrderQty())) {

                  Optional<Map<String, Object>> openOrderOpt = bitmexService.get_Order_Order(follower)
                          .stream()
                          .filter(i -> dataOrder.getSymbol().name().equals(i.get("symbol").toString()))
                          .filter(i -> "Limit".equals(i.get("ordType").toString()))
                          .filter(i -> "New".equals(i.get("ordStatus").toString()))
                          .findFirst();

                  if (openOrderOpt.isPresent()) {
                    dataOrder.withOrderQty(openOrderOpt.get().get("orderQty").toString());

                  } else {
                    dataOrder.withOrderQty(
                            calculateFixedQtyForSymbol(
                                    follower, dataOrder.getSymbol(), dataLeverage.getLeverage(),
                                    getSymbolLastPrice(follower, dataOrder.getSymbol())
                            )
                    );
                  }
                }
              } else if (OrderType.Limit.equals(dataOrder.getOrderType())){
                dataOrder.withOrderQty(
                        calculateFixedQtyForSymbol(
                                follower, dataOrder.getSymbol(), dataLeverage.getLeverage(), dataOrder.getPrice()
                        )
                );
              } else if (OrderType.Market.equals(dataOrder.getOrderType())){
                dataOrder.withOrderQty(
                        calculateFixedQtyForSymbol(
                                follower, dataOrder.getSymbol(), dataLeverage.getLeverage(),
                                bitmexService.getInstrumentLastPrice(follower, dataOrder.getSymbol())
                        )
                );
              }
              bitmexService.post_Order_Order(follower, dataOrder.withClOrdId(uniqueclOrdID));

            } catch (Exception e) {
              logger.error("Order failed for follower: " + follower.getUsername());
            }
          });
        }
      }
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try {
        fut.get();
      } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e.getCause());
      }
    });
  }

  public void postOrderWithPercentage(User trader, DataOrderBuilder dataOrderBuilder, int percentage) {
    List<User> enabledfollowers = userService.getEnabledFollowers(trader);
    final String uniqueclOrdID = UUID.randomUUID().toString();

    Future<?> future = null;
    for (User follower: enabledfollowers) {
      future = multiExecutor.submit(() -> {
        try {
          long qty = 0L;
          Map<String, Object> position = bitmexService.get_Position(follower)
                  .stream()
                  .filter(i -> i.get("symbol").toString().equals(dataOrderBuilder.getSymbol().name()))
                  .findAny()
                  .orElse(Collections.emptyMap());

          if (!position.isEmpty()) {
            qty = Long.valueOf(position.get("currentQty").toString());

            long finalQty = Math.abs(qty * percentage / 100);

            dataOrderBuilder.withOrderQty(Long.toString(finalQty)).withClOrdId(uniqueclOrdID);

            bitmexService.post_Order_Order(follower, dataOrderBuilder);
          }

        } catch (Exception e) {
          logger.error("Order failed for follower: " + follower.getUsername());
        }
      });
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try {
        fut.get();
      } catch (InterruptedException | ExecutionException e) {
        logger.error(e.getMessage(), e.getCause());
      }
    });
  }

  public List<Map<String, Object>> getRandomActiveOrders(User trader) {
    return getActiveOrdersOf(userService.getGuideFollower(trader));
  }

  public List<Map<String, Object>> getActiveOrdersOf(User user) {
    return bitmexService.get_Order_Order(user)
            .stream()
            .filter(order -> Arrays.stream(Symbol.values())
                    .map(Symbol::name)
                    .collect(Collectors.toList())
                    .contains(order.get("symbol").toString()))
            .filter(i -> i.get("ordStatus").equals("New"))
            .collect(Collectors.toList());
  }

  public List<Map<String, Object>> getRandomPositions(User trader) {
    return getPositionsOf(userService.getGuideFollower(trader));
  }

  public List<Map<String, Object>> getPositionsOf(User user) {
    return bitmexService.get_Position(user)
            .stream()
            .filter(pos -> Arrays.stream(Symbol.values())
                    .map(Symbol::name)
                    .collect(Collectors.toList())
                    .contains(pos.get("symbol").toString()))
            .filter(pos -> pos.get("avgEntryPrice") != null)
            .collect(Collectors.toList());
  }

  public List<Map<String, Object>> getRandomTX(User trader) {
    List<Map<String, Object>> randomTX;

    List<User> enabledfollowers = userService.getEnabledFollowers(trader);

    for (User f: enabledfollowers) {
      randomTX = bitmexService.get_Order_Order(f);

      if (!randomTX.isEmpty())
        return randomTX;
    }

    return Collections.emptyList();
  }

  public void cancelOrder(User trader, DataDeleteOrderBuilder dataDeleteOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexService.cancelOrder(follower, dataDeleteOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
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
        e.printStackTrace();
      }
    });
  }

  public void closeAllPosition(User trader, DataOrderBuilder dataOrder) {
    Future<?> future = null;

    for (User follower: userService.getEnabledFollowers(trader)) {
      future = multiExecutor.submit(() -> bitmexService.post_Order_Order(follower, dataOrder));
    }
    Optional.ofNullable(future).ifPresent(fut -> {
      try { fut.get(); } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });
  }

  public void panicButton(User trader) {
    DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder();
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder();

    for (Symbol symbol: Symbol.values()) {
      dataDeleteOrderBuilder.withSymbol(symbol);
      cancelAllOrders(trader, dataDeleteOrderBuilder);

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
      sumFixedQtys.put(Symbol.ADAH19.name(), sumFixedQtys.get(Symbol.ADAH19.name()) + f.getFixedQtyADAZ18());
      sumFixedQtys.put(Symbol.BCHH19.name(), sumFixedQtys.get(Symbol.BCHH19.name()) + f.getFixedQtyBCHZ18());
      sumFixedQtys.put(Symbol.EOSH19.name(), sumFixedQtys.get(Symbol.EOSH19.name()) + f.getFixedQtyEOSZ18());
      sumFixedQtys.put(Symbol.ETHH19.name(), sumFixedQtys.get(Symbol.ETHH19.name()) + f.getFixedQtyETHUSD());
      sumFixedQtys.put(Symbol.LTCH19.name(), sumFixedQtys.get(Symbol.LTCH19.name()) + f.getFixedQtyLTCZ18());
      sumFixedQtys.put(Symbol.TRXH19.name(), sumFixedQtys.get(Symbol.TRXH19.name()) + f.getFixedQtyTRXZ18());
      sumFixedQtys.put(Symbol.XRPH19.name(), sumFixedQtys.get(Symbol.XRPH19.name()) + f.getFixedQtyXRPZ18());
    });

    return sumFixedQtys;
  }

  Map<String, Double> calculateSumPositions(List<User> followers) {
    Map<String, Double> sumPositions = new HashMap<>();
    Arrays.stream(Symbol.values()).forEach(symbol -> sumPositions.put(symbol.name(), (double) 0));

    try {
      followers.forEach(f -> bitmexService.get_Position(f)
              .stream()
              .filter(Objects::nonNull)
              .forEach(map -> {
                String sym = map.get("symbol").toString();
                sumPositions.put(sym, sumPositions.get(sym) + Double.parseDouble(map.get("currentQty").toString()));
              })
      );
    } catch (Exception ex){
      logger.debug("Eskase h calculateSumPositions", ex);
    }
    return sumPositions;
  }

  private String getSymbolLastPrice(User user, Symbol symbol) {
    return bitmexService.getInstrumentLastPrice(user, symbol);
  }

  private String calculateFixedQtyForSymbol(User user, Symbol symbol, String leverage, String lastPrice) {
    if (symbol.equals(Symbol.XBTUSD))
      return calculateOrderQtyXBTUSD(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.ETHUSD))
      return calculateOrderQtyETHUSD(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.ADAH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.BCHH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.EOSH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.ETHH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.LTCH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.TRXH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);
    if (symbol.equals(Symbol.XRPH19))
      return calculateOrderQtyOther(user, 10, leverage, lastPrice);

    throw new RuntimeException("Fixed qty user calculation failed");
  }

  private String calculateOrderQtyXBTUSD(User user, double fixedQty, String lev, String lastPrice) {
    return String.valueOf(Math.round(xbtAmount(user, fixedQty) * leverage(lev) * lastPrice(lastPrice)));
  }

  private String calculateOrderQtyOther(User user, double fixedQty, String lev, String lastPrice) {
    return String.valueOf(Math.round(xbtAmount(user, fixedQty) * leverage(lev) / lastPrice(lastPrice)));
  }


  private String calculateOrderQtyETHUSD(User user, double fixedQty, String lev, String lastPrice) {
    return String.valueOf(Math.round((xbtAmount(user, fixedQty) * leverage(lev)) / (lastPrice(lastPrice) * 0.000001)));
  }

  private Double xbtAmount(User user, double fixedQty) {
    return (fixedQty / 100) * (((Integer) bitmexService.get_User_Margin(user).get("walletBalance")).doubleValue() / 100000000);
  }

  private Double leverage(String leverage) {
    return Double.parseDouble(leverage);
  }

  private Double lastPrice(String lastPrice) {
    return Double.parseDouble(lastPrice);
  }

}
