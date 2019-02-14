package com.ntouzidis.cooperative.module.trade;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import net.bull.javamelody.internal.model.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeService {

    Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final BitmexService bitmexService;
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TradeService(BitmexService bitmexService,
                        UserService userService) {
        this.bitmexService = bitmexService;
        this.userService = userService;
    }

    public void placeOrderAll(User trader, DataPostLeverage dataPostLeverage, DataPostOrderBuilder dataPostOrder) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        enabledfollowers.forEach(customer -> {
            bitmexService.post_Position_Leverage(customer, dataPostLeverage);

            bitmexService.post_Order_Order_WithFixeds(customer, dataPostOrder);
        });
    }

    public void createSignal(User trader, SignalBuilder sb) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        String uniqueclOrdID1 = UUID.randomUUID().toString();
        String uniqueclOrdID2 = UUID.randomUUID().toString();
        String uniqueclOrdID3 = UUID.randomUUID().toString();

        DataPostLeverage dataLeverage = new DataPostLeverage()
                .withSymbol(sb.getSymbol())
                .withLeverage(sb.getLeverage());

        enabledfollowers.forEach(customer -> {
            //            1. Set Leverage
            bitmexService.post_Position_Leverage(customer, dataLeverage);

            //            2. Market
            DataPostOrderBuilder marketDataOrder = new DataPostOrderBuilder()
                    .withClOrdId(uniqueclOrdID1)
                    .withOrderType("Market")
                    .withSymbol(sb.getSymbol())
                    .withSide(sb.getSide())
                    .withText("Bitmexcallbot");

            bitmexService.post_Order_Order_WithFixeds(customer, marketDataOrder);


            //            3. Stop Market
            if (sb.getStopLoss() != null) {
                DataPostOrderBuilder stopMarketDataOrder = new DataPostOrderBuilder()
                        .withClOrdId(uniqueclOrdID2)
                        .withOrderType("Stop")
                        .withSymbol(sb.getSymbol())
                        .withSide(sb.getSide().equals("Buy")?"Sell":"Buy")
                        .withExecInst("Close,LastPrice")
                        .withStopPrice(sb.getStopLoss())
                        .withText("Bitmexcallbot");

                bitmexService.post_Order_Order_WithFixeds(customer, stopMarketDataOrder);
            }

            //            4. Limit
            if (sb.getProfitTrigger() != null) {
                DataPostOrderBuilder limitDataOrder = new DataPostOrderBuilder()
                        .withClOrdId(uniqueclOrdID3)
                        .withOrderType("Limit")
                        .withSymbol(sb.getSymbol())
                        .withSide(sb.getSide().equals("Buy")?"Sell":"Buy")
                        .withPrice(sb.getProfitTrigger())
                        .withText("Bitmexcallbot");

                bitmexService.post_Order_Order_WithFixeds(customer, limitDataOrder);
            }
        });
    }

    public List<Map<String, Object>> getRandomActiveOrders(User trader) {
        User guideFollower = userService.getGuideFollower(trader);
        return bitmexService.get_Order_Order(guideFollower)
                .stream()
                .filter(order -> Arrays.stream(Symbol.values())
                        .map(Symbol::getValue)
                        .collect(Collectors.toList())
                        .contains(order.get("symbol").toString()))
                .filter(i -> i.get("ordStatus").equals("New"))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getRandomPositions(User trader) {
        User guideFollower = userService.getGuideFollower(trader);
        return bitmexService.get_Position(guideFollower);
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
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        enabledfollowers.forEach(customer -> bitmexService.cancelAllOrders(customer, dataDeleteOrderBuilder));
    }

    public void postOrder2(User trader, DataPostOrderBuilder dataPostOrderBuilder, int percentage) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        enabledfollowers.forEach(customer -> bitmexService.post_Order_Order_WithFixedsAndPercentage(customer, dataPostOrderBuilder, percentage));
    }

    public void closeAllPosition(User trader, DataPostOrderBuilder dataPostOrderBuilder) {
        List<User> enabledfollowers = userService.getEnabledFollowers(trader);

        enabledfollowers.forEach(customer -> bitmexService.post_Order_Order(customer, dataPostOrderBuilder));
    }

    Map<String, Long> calculateSumFixedQtys(List<User> followers) {
        Map<String, Long> sumFixedQtys = new HashMap<>();
        Arrays.stream(Symbol.values()).forEach(symbol -> sumFixedQtys.put(symbol.getValue(), 0L));

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

}
