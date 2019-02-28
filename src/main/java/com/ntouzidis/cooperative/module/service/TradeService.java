package com.ntouzidis.cooperative.module.service;

import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class TradeService {

    private Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final BitmexService bitmexService;
    private final UserService userService;
    private final ExecutorService multiExecutor;

    public TradeService(
            BitmexService bitmexService,
            UserService userService,
            ExecutorService multiExecutor
    ) {
        this.bitmexService = bitmexService;
        this.userService = userService;
        this.multiExecutor = multiExecutor;
    }

    public void placeOrderAll(User trader, DataPostLeverage dataPostLeverage, DataPostOrderBuilder dataPostOrder) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        String uniqueclOrdID = UUID.randomUUID().toString();

        Future<?> future = null;

        for (User follower : enabledfollowers) {
            try {
                future = multiExecutor.submit(() -> bitmexService.post_Position_Leverage(follower, dataPostLeverage));
            } catch (Exception e) {

            }
        }

        if (Optional.ofNullable(future).isPresent()) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (future.isDone()) {
                for (User follower : enabledfollowers) {
                    future = multiExecutor.submit(() -> {
                        try {
                            if (OrderType.Stop.equals(dataPostOrder.getOrderType()) || OrderType.StopLimit.equals(dataPostOrder.getOrderType())) {
                                dataPostOrder.withOrderQty(
                                        String.valueOf(Math.abs((Integer)
                                                bitmexService.getSymbolPosition(follower, dataPostLeverage.getSymbol())
                                                        .get("currentQty"))
                                        )
                                );
                                if ("0".equals(dataPostOrder.getOrderQty())) {
                                    dataPostOrder.withOrderQty(
                                            calculateFixedQtyForSymbol(
                                                    follower,
                                                    dataPostOrder.getSymbol(),
                                                    dataPostLeverage.getLeverage(),
                                                    getSymbolLastPrice(dataPostOrder.getSymbol())
                                            )
                                    );
                                }
                            } else {
                                dataPostOrder.withOrderQty(
                                        calculateFixedQtyForSymbol(
                                                follower,
                                                dataPostOrder.getSymbol(),
                                                dataPostLeverage.getLeverage(),
                                                getSymbolLastPrice(dataPostOrder.getSymbol())
                                        )
                                );
                            }
                            bitmexService.post_Order_Order(follower, dataPostOrder.withClOrdId(uniqueclOrdID));

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
                if (fut.isDone()) {
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public void postOrderWithPercentage(User trader, DataPostOrderBuilder dataPostOrderBuilder, int percentage) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        Future<?> future = null;
        long start = System.nanoTime();
        for (User follower: enabledfollowers) {
            future = multiExecutor.submit(() -> {
                try {
                    long qty = 0L;
                    Map<String, Object> position = bitmexService.get_Position(follower)
                                    .stream()
                                    .filter(i -> i.get("symbol").toString().equals(dataPostOrderBuilder.getSymbol().getValue()))
                                    .findAny()
                                    .orElse(Collections.emptyMap());

                    if (!position.isEmpty())
                        qty = Long.valueOf(position.get("currentQty").toString());

                    long finalQty = Math.abs(qty * percentage / 100);

                    dataPostOrderBuilder.withOrderQty(Long.toString(finalQty));

                    bitmexService.post_Order_Order(follower, dataPostOrderBuilder);

                } catch (Exception e) {
                    logger.error("Order failed for follower: " + follower.getUsername());
                }
            });
        }
        Optional.ofNullable(future).ifPresent(fut -> {
            try {
                fut.get();
                if (fut.isDone()) {
                    long end2 = System.nanoTime();
                    long duration2 = (end2 - start) / 1000000;
                    logger.info("Order with percentage took " + duration2 + "milliseconds to complete for " + enabledfollowers.size() + " followers");
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public void createSignal(User trader, SignalBuilder sb) {
//        List<User> enabledfollowers = userService.getEnabledFollowers(trader);
//
//        // unique id's to mark the following orders
//        String uniqueclOrdID1 = UUID.randomUUID().toString();
//        String uniqueclOrdID2 = UUID.randomUUID().toString();
//        String uniqueclOrdID3 = UUID.randomUUID().toString();
//
//        DataPostLeverage dataLeverage = new DataPostLeverage()
//                .withSymbol(sb.getSymbol())
//                .withLeverage(sb.getLeverage());
//
//        enabledfollowers.forEach(customer -> {
//            try {
//                // 1. Set Leverage
//                bitmexService.post_Position_Leverage(customer, dataLeverage);
//
//                // 2. Market
//                DataPostOrderBuilder marketDataOrder = new DataPostOrderBuilder()
//                        .withClOrdId(uniqueclOrdID1)
//                        .withOrderType(OrderType.Market)
//                        .withSymbol(sb.getSymbol())
//                        .withSide(sb.getSide());
//
//                bitmexService.post_Order_Order_WithFixeds(customer, marketDataOrder);
//
//                // 3. Stop Market
//                if (sb.getStopLoss() != null) {
//                    DataPostOrderBuilder stopMarketDataOrder = new DataPostOrderBuilder()
//                            .withClOrdId(uniqueclOrdID2)
//                            .withOrderType(OrderType.Stop)
//                            .withSymbol(sb.getSymbol())
//                            .withSide(sb.getSide().equals(Side.Buy) ? Side.Sell : Side.Buy)
//                            .withExecInst("Close,LastPrice")
//                            .withStopPrice(sb.getStopLoss());
//
//                    bitmexService.post_Order_Order_WithFixeds(customer, stopMarketDataOrder);
//                }
//
//                // 4. Limit
//                if (sb.getProfitTrigger() != null) {
//                    DataPostOrderBuilder limitDataOrder = new DataPostOrderBuilder()
//                            .withClOrdId(uniqueclOrdID3)
//                            .withOrderType(OrderType.Limit)
//                            .withSymbol(sb.getSymbol())
//                            .withSide(sb.getSide().equals(Side.Buy) ? Side.Sell : Side.Buy)
//                            .withPrice(sb.getProfitTrigger());
//
//                    bitmexService.post_Order_Order_WithFixeds(customer, limitDataOrder);
//                }
//            } catch (Exception e) {
//                logger.error("Order failed for follower: " + customer.getUsername());
//            }
//        });
    }

    public void panicButton(User trader) {
        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder();
        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder();

        for (Symbol symbol: Symbol.values()) {
            dataDeleteOrderBuilder.withSymbol(symbol);
            cancelAllOrders(trader, dataDeleteOrderBuilder);

            dataPostOrderBuilder.withSymbol(symbol).withOrderType(OrderType.Market).withExecInst("Close");
            closeAllPosition(trader, dataPostOrderBuilder);
        }
    }

    public List<Map<String, Object>> getRandomActiveOrders(User trader) {
        return getRandomActiveOrdersOf(userService.getGuideFollower(trader));
    }

    public List<Map<String, Object>> getRandomActiveOrdersOf(User user) {
        return bitmexService.get_Order_Order(user)
                .stream()
                .filter(order -> Arrays.stream(Symbol.values())
                        .map(Symbol::getValue)
                        .collect(Collectors.toList())
                        .contains(order.get("symbol").toString()))
                .filter(i -> i.get("ordStatus").equals("New"))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRandomPositions(User trader) {
        return getRandomPositionsOf(userService.getGuideFollower(trader));
    }

    public List<Map<String, Object>> getRandomPositionsOf(User user) {
        return bitmexService.get_Position(user)
                .stream()
                .filter(pos -> Arrays.stream(Symbol.values())
                        .map(Symbol::getValue)
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

    public void cancelOrder(User trader, DataDeleteOrderBuilder dataDeleteOrderBuilder) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        enabledfollowers.forEach(customer -> bitmexService.cancelOrder(customer, dataDeleteOrderBuilder));
    }

    public void cancelAllOrders(User trader, DataDeleteOrderBuilder dataDeleteOrderBuilder) {
        Future<?> future = null;
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        for (User follower: enabledfollowers) {
            future = multiExecutor.submit(() -> bitmexService.cancelAllOrders(follower, dataDeleteOrderBuilder));
        }
        Optional.ofNullable(future).ifPresent(fut -> {
            try { fut.get(); } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public void closeAllPosition(User trader, DataPostOrderBuilder dataPostOrderBuilder) {
        Future<?> future = null;
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        for (User follower: enabledfollowers) {
            future = multiExecutor.submit(() -> bitmexService.post_Order_Order(follower, dataPostOrderBuilder));
        }
        Optional.ofNullable(future).ifPresent(fut -> {
            try { fut.get(); } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    Map<String, Double> calculateSumFixedQtys(List<User> followers) {
        Map<String, Double> sumFixedQtys = new HashMap<>();
        Arrays.stream(Symbol.values()).forEach(symbol -> sumFixedQtys.put(symbol.getValue(), (double) 0));

        followers.forEach(f -> {
            sumFixedQtys.put(Symbol.XBTUSD.getValue(), sumFixedQtys.get(Symbol.XBTUSD.getValue()) + f.getFixedQtyXBTUSD());
            sumFixedQtys.put(Symbol.ETHUSD.getValue(), sumFixedQtys.get(Symbol.ETHUSD.getValue()) + f.getFixedQtyXBTJPY());
            sumFixedQtys.put(Symbol.ADAXXX.getValue(), sumFixedQtys.get(Symbol.ADAXXX.getValue()) + f.getFixedQtyADAZ18());
            sumFixedQtys.put(Symbol.BCHXXX.getValue(), sumFixedQtys.get(Symbol.BCHXXX.getValue()) + f.getFixedQtyBCHZ18());
            sumFixedQtys.put(Symbol.EOSXXX.getValue(), sumFixedQtys.get(Symbol.EOSXXX.getValue()) + f.getFixedQtyEOSZ18());
            sumFixedQtys.put(Symbol.ETHXXX.getValue(), sumFixedQtys.get(Symbol.ETHXXX.getValue()) + f.getFixedQtyETHUSD());
            sumFixedQtys.put(Symbol.LTCXXX.getValue(), sumFixedQtys.get(Symbol.LTCXXX.getValue()) + f.getFixedQtyLTCZ18());
            sumFixedQtys.put(Symbol.TRXXXX.getValue(), sumFixedQtys.get(Symbol.TRXXXX.getValue()) + f.getFixedQtyTRXZ18());
            sumFixedQtys.put(Symbol.XRPXXX.getValue(), sumFixedQtys.get(Symbol.XRPXXX.getValue()) + f.getFixedQtyXRPZ18());
        });

        return sumFixedQtys;
    }

    Map<String, Double> calculateSumPositions(List<User> followers) {
        Map<String, Double> sumPositions = new HashMap<>();
        Arrays.stream(Symbol.values()).forEach(symbol -> sumPositions.put(symbol.getValue(), (double) 0));

        try {
            followers.forEach(f -> bitmexService.getAllSymbolPosition(f)
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

    private String getSymbolLastPrice(Symbol symbol) {
        return bitmexService.getInstrumentLastPrice(
                userService.findCustomer("gejocust").orElseThrow(() ->
                        new RuntimeException("Customer not found")
                ),
                Symbol.valueOf(symbol.getValue())
        );
    }

    private String calculateFixedQtyForSymbol(User user, Symbol symbol, String leverage, String lastPrice) {
        if (symbol.equals(Symbol.XBTUSD))
            return calculateOrderQty(user, user.getFixedQtyXBTUSD(), leverage, lastPrice);
        if (symbol.equals(Symbol.ETHUSD))
            return calculateOrderQtyETHUSD(user, user.getFixedQtyETHUSD(), leverage, lastPrice);
        if (symbol.equals(Symbol.ADAXXX))
            return calculateOrderQty(user, user.getFixedQtyADAZ18(), leverage, lastPrice);
        if (symbol.equals(Symbol.BCHXXX))
            return calculateOrderQty(user, user.getFixedQtyBCHZ18(), leverage, lastPrice);
        if (symbol.equals(Symbol.EOSXXX))
            return calculateOrderQty(user, user.getFixedQtyEOSZ18(), leverage, lastPrice);
        if (symbol.equals(Symbol.ETHXXX))
            return calculateOrderQty(user, user.getFixedQtyXBTJPY(), leverage, lastPrice);
        if (symbol.equals(Symbol.LTCXXX))
            return calculateOrderQty(user, user.getFixedQtyLTCZ18(), leverage, lastPrice);
        if (symbol.equals(Symbol.TRXXXX))
            return calculateOrderQty(user, user.getFixedQtyTRXZ18(), leverage, lastPrice);
        if (symbol.equals(Symbol.XRPXXX))
            return calculateOrderQty(user, user.getFixedQtyXRPZ18(), leverage, lastPrice);

        throw new RuntimeException("Fixed qty user calculation failed");
    }

    private String calculateOrderQty(User user, double fixedQty, String lev, String lastPrice) {
        return String.valueOf(Math.round(
                xbtAmount(user, fixedQty) * leverage(lev) * lastPrice(lastPrice)
        ));
    }

    private String calculateOrderQtyETHUSD(User user, double fixedQty, String lev, String lastPrice) {
        return String.valueOf(Math.round(
                (xbtAmount(user, fixedQty) * leverage(lev)) / (lastPrice(lastPrice) * 0.000001)
        ));
    }

    private Double leverage(String leverage) {
        return Double.parseDouble(leverage);
    }

    private Double lastPrice(String lastPrice) {
        return Double.parseDouble(lastPrice);
    }

    private Double xbtAmount(User user, double fixedQty) {
        return (fixedQty / 100) * (((Integer) bitmexService.get_User_Margin(user).get("walletBalance")).doubleValue() / 100000000);
    }

}
