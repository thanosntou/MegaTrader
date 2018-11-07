package com.ntouzidis.cooperative.module.trade;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TradeService {

    private final BitmexService bitmexService;
    private final UserService userService;

    public TradeService(BitmexService bitmexService,
                        UserService userService) {
        this.bitmexService = bitmexService;
        this.userService = userService;
    }

    void placeOrderAll(User trader, DataPostLeverage dataPostLeverage, DataPostOrderBuilder dataPostOrder) {
        List<User> followers = userService.getFollowers(trader);

        followers.forEach(customer -> {
            bitmexService.post_Position_Leverage(customer, dataPostLeverage);

            bitmexService.post_Order_Order_WithFixeds(customer, dataPostOrder);
        });

    }

    void createSignal(User trader, SignalBuilder sb) {
        List<User> followers = userService.getFollowers(trader);

        DataPostLeverage dataLeverage = new DataPostLeverage()
                .withSymbol(sb.getSymbol())
                .withLeverage(sb.getLeverage());

        followers.forEach(customer -> {
            //            1. Set Leverage
            bitmexService.post_Position_Leverage(customer, dataLeverage);

            //            2. Market
            DataPostOrderBuilder marketDataOrder = new DataPostOrderBuilder()
                    .withOrderType("Market")
                    .withSymbol(sb.getSymbol())
                    .withSide(sb.getSide())
                    .withText("Bitcoin Syndicate");

            bitmexService.post_Order_Order_WithFixeds(customer, marketDataOrder);


            //            3. Stop Market
            if (sb.getStopLoss() != null) {
                DataPostOrderBuilder stopMarketDataOrder = new DataPostOrderBuilder()
                        .withOrderType("Stop")
                        .withSymbol(sb.getSymbol())
                        .withSide(sb.getSide().equals("Buy")?"Sell":"Buy")
                        .withExecInst("Close,LastPrice")
                        .withStopPrice(sb.getStopLoss())
                        .withText("Bitcoin Syndicate");

                bitmexService.post_Order_Order_WithFixeds(customer, stopMarketDataOrder);
            }

            //            4. Limit
            if (sb.getProfitTrigger() != null) {
                DataPostOrderBuilder limitDataOrder = new DataPostOrderBuilder()
                        .withOrderType("Limit")
                        .withSymbol(sb.getSymbol())
                        .withSide(sb.getSide().equals("Buy")?"Sell":"Buy")
                        .withPrice(sb.getProfitTrigger())
                        .withText("Bitcoin Syndicate");

                bitmexService.post_Order_Order_WithFixeds(customer, limitDataOrder);
            }
        });
    }

    List<Map<String, Object>> getRandomActiveOrders(User trader) {
        List<Map<String, Object>> randomAllOrders;

        LinkedList<User> followers = new LinkedList<>(userService.getFollowers(trader));

        for (User f: followers) {
            randomAllOrders = bitmexService.get_Order_Order(f);

            if (randomAllOrders != null)
                return randomAllOrders;
        }

        return Collections.emptyList();
    }

    List<Map<String, Object>> getRandomPositions(User trader) {
        List<Map<String, Object>> randomPositions;

        List<User> followers = userService.getFollowers(trader);

        for (User f: followers) {
            randomPositions = bitmexService.get_Position(f);

            if (randomPositions != null)
                return randomPositions;
        }

        return Collections.emptyList();
    }

    void cancelOrder(User trader, DataDeleteOrderBuilder dataDeleteOrderBuilder) {
        List<User> followers = userService.getFollowers(trader);

        followers.forEach(customer -> bitmexService.cancelOrder(customer, dataDeleteOrderBuilder));
    }

    void cancelAllOrders(User trader, DataDeleteOrderBuilder dataDeleteOrderBuilder) {
        List<User> followers = userService.getFollowers(trader);

        followers.forEach(customer -> bitmexService.cancelAllOrders(customer, dataDeleteOrderBuilder));
    }

    void positionAll(User trader, DataPostOrderBuilder dataPostOrderBuilder, int percentage) {
        List<User> followers = userService.getFollowers(trader);

        followers.forEach(customer -> bitmexService.post_Order_Order_WithFixedsAndPercentage(customer, dataPostOrderBuilder, percentage));
    }

    void closeAllPosition(User trader, DataPostOrderBuilder dataPostOrderBuilder) {
        List<User> followers = userService.getFollowers(trader);

        followers.forEach(customer -> bitmexService.post_Order_Order(customer, dataPostOrderBuilder));
    }

//    private Long calculateFixedQtyForWantedSymbol(User user, String symbol) {
//        Long wantedFixQty = 0L;
//
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyXBTUSD();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyXBTJPY();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyADAZ18();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyBCHZ18();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyEOSZ18();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyETHUSD();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyLTCZ18();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyTRXZ18();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyXRPZ18();
//        if (symbol.equals("XBTUSD")) wantedFixQty = user.getFixedQtyXBTKRW();
//
//        return wantedFixQty;
//    }

    Map<String, String> calculateSumFixedQtys(List<User> followers) {
        Map<String, String> sumFixedQtys = new HashMap<>();

        long sumXBTUSD = 0;
        long sumXBTJPY = 0;
        long sumADAZ18 = 0;
        long sumBCHZ18 = 0;
        long sumEOSZ18 = 0;
        long sumETHUSD = 0;
        long sumLTCZ18 = 0;
        long sumTRXZ18 = 0;
        long sumXRPZ18 = 0;
        long sumXBTKRW = 0;

        for(User f: followers){
            sumXBTUSD += f.getFixedQtyXBTUSD().intValue();
            sumXBTJPY += f.getFixedQtyXBTJPY().intValue();
            sumADAZ18 += f.getFixedQtyADAZ18().intValue();
            sumBCHZ18 += f.getFixedQtyBCHZ18().intValue();
            sumEOSZ18 += f.getFixedQtyEOSZ18().intValue();
            sumETHUSD += f.getFixedQtyETHUSD().intValue();
            sumLTCZ18 += f.getFixedQtyLTCZ18().intValue();
            sumTRXZ18 += f.getFixedQtyTRXZ18().intValue();
            sumXRPZ18 += f.getFixedQtyXRPZ18().intValue();
            sumXBTKRW += f.getFixedQtyXBTKRW().intValue();
        }

        sumFixedQtys.put("sumXBTUSD", String.valueOf(sumXBTUSD));
        sumFixedQtys.put("sumXBTJPY", String.valueOf(sumXBTJPY));
        sumFixedQtys.put("sumADAZ18", String.valueOf(sumADAZ18));
        sumFixedQtys.put("sumBCHZ18", String.valueOf(sumBCHZ18));
        sumFixedQtys.put("sumEOSZ18", String.valueOf(sumEOSZ18));
        sumFixedQtys.put("sumETHUSD", String.valueOf(sumETHUSD));
        sumFixedQtys.put("sumLTCZ18", String.valueOf(sumLTCZ18));
        sumFixedQtys.put("sumTRXZ18", String.valueOf(sumTRXZ18));
        sumFixedQtys.put("sumXRPZ18", String.valueOf(sumXRPZ18));
        sumFixedQtys.put("sumXBTKRW", String.valueOf(sumXBTKRW));

        return sumFixedQtys;
    }

}
