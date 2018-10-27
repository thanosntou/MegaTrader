package com.ntouzidis.cooperative.module.trade;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.bitmex.IBitmexService;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.lang.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trade")
public class TradeController {

    private final IBitmexService bitmexService;
    private final UserService userService;
    private final TradeService tradeService;

    public TradeController(BitmexService bitmexService, UserService userService, TradeService tradeService) {
        this.bitmexService = bitmexService;
        this.userService = userService;
        this.tradeService = tradeService;
    }

    @GetMapping(value = {"", "/"})
    public String showDefault() {
        return "redirect:/trade/XBTUSD";
    }

    @GetMapping("/{symbol}")
    public String showProducts(@PathVariable(name = "symbol") String symbol,
                               Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        List<User> followers = userService.getFollowers(user);
        List<Map<String, Object>> positions = bitmexService.get_Position(user);

//      --------- Current Leverage --------------
        String currentLeverage = null;
        if (!positions.isEmpty()) currentLeverage = String.valueOf(positions.stream().filter(i -> i.get("symbol").equals(symbol)).map(i -> i.get("leverage")).findAny().orElse(null));

//      --------------- Sum of Fixed Customer Qty --------------
        Map<String, String> sumFixedQtys = tradeService.calculateSumFixedQtys(followers);

//      --------------- maxLeverage and piceStep ---------------
        String maxLeverage = "0";
        String priceStep = "1";
        Map<String, String> m = calculateMaxLeverageAndPriceStep(symbol, maxLeverage, priceStep);
        maxLeverage = m.get("maxleverage");
        priceStep = m.get("priceStep");

//      --------------------  sumPosition + any customer position (temporary)------------
        double sumPosition = followers.stream().map(i -> bitmexService.getSymbolPosition(i, symbol)).filter(Objects::nonNull).mapToDouble(tempPos -> Double.parseDouble(tempPos.get("currentQty").toString())).sum();
        List<Map<String, Object>> openPositions = positions;


        model.addAttribute("user", user);
        model.addAttribute("symbol", symbol);
        model.addAttribute("maxLeverage", maxLeverage);
        model.addAttribute("priceStep", priceStep);
        model.addAttribute("followers", followers);
        model.addAttribute("sumFixedQtys", sumFixedQtys);
        model.addAttribute("currentLeverage", currentLeverage);
        model.addAttribute("page", "trade");
        model.addAttribute("sumPosition", sumPosition);
        model.addAttribute("openPositions", openPositions);

        return "trade-panel2";
    }

    @PostMapping(value = "/signal")
    public String createSignal(@RequestParam(name="symbol", required = false) String symbol,
                               @RequestParam(name="side") String side,
                               @RequestParam(name="leverage", required = false) String leverage,
                               @RequestParam(name="stopLoss", required = false) String stopLoss,
                               @RequestParam(name="profitTrigger", required = false) String profitTrigger,
                               Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        if (symbol == null) symbol = "XBTUSD";

        SignalBuilder signalBuilder = new SignalBuilder()
                .withSymbol(symbol).withSide(side).withleverage(leverage)
                .withStopLoss(stopLoss).withProfitTrigger(profitTrigger);

        tradeService.createSignal(user, signalBuilder);

        model.addAttribute("user", user);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam(name="symbol", required = false) String symbol,
                              @RequestParam(name="orderID") String orderID,
                              Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                .withOrderID(orderID)
                .withText("Cancel from Bitcoin Syndicate");

        tradeService.cancelOrder(user, dataDeleteOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/order/cancelAll")
    public String cancelOrder(@RequestParam(name="symbol", required = false) String symbol,
                              Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                .withText("Canceled All from Bitcoin Syndicate");

        tradeService.cancelAllOrders(user, dataDeleteOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/positionAll")
    public String positionAll(@RequestParam(name="symbol") String symbol,
                              @RequestParam("orderType") String orderType,
                              @RequestParam("side") String side,
                              @RequestParam("percentage") int percentage,
                              @RequestParam("price") String price,
                              Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withSide(side)
                .withOrderType(orderType)
                .withText("Bitcoin Syndicate");

        if (orderType.equals("Limit")) dataPostOrderBuilder.withPrice(price);

        tradeService.positionAll(user, dataPostOrderBuilder, percentage);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/position/close")
    public String closePosition(@RequestParam(name="symbol") String symbol,
                                 Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withExecInst("Close")
                .withOrderType("Market")
                .withText("Position close from Bitcoin Syndicate");

        tradeService.closePosition(user, dataPostOrderBuilder);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/trade/" + symbol;
    }

    @PostMapping(value = "/orderAll")
    public String postOrder(@RequestParam(name="symbol") String symbol,
                            @RequestParam(name="side") String side,
                            @RequestParam(name="ordType") String ordType,
                            @RequestParam(name="price", required=false) String price,
                            @RequestParam(name="execInst", required=false) String execInst,
                            @RequestParam(name="stopPx", required = false) String stopPx,
                            @RequestParam(name="leverage", required = false) String leverage,
                            Model model, Principal principal) {

        User trader = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage().withSymbol(symbol).withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder().withSymbol(symbol)
                .withSide(side).withOrderType(ordType)
                .withPrice(price).withExecInst(execInst).withStopPrice(stopPx);


        tradeService.placeOrderForCustomers(trader, dataLeverageBuilder, dataOrderBuilder);

        model.addAttribute("user", trader);
        model.addAttribute("user", principal.getName());

        return "redirect:/trade/"+symbol;
    }

    private Map<String, String> calculateMaxLeverageAndPriceStep(String symbol, String maxLeverage, String priceStep) {
        if (symbol.equals("XBTUSD")) {
            maxLeverage = "100";
            priceStep = "0.1";
        }
        if (symbol.equals("XBTJPY")) {
            maxLeverage = "100";
        }

        if (symbol.equals("ADAZ18")) {
            maxLeverage = "20";
            priceStep = "0.00000001";
        }

        if (symbol.equals("BCHZ18")) {
            maxLeverage = "20";
            priceStep = "0.0001";
        }

        if (symbol.equals("EOSZ18")) {
            maxLeverage = "20";
            priceStep = "0.0000001";
        }

        if (symbol.equals("ETHUSD")) {
            maxLeverage = "50";
            priceStep = "0.01";
        }

        if (symbol.equals("LTCZ18")) {
            maxLeverage = "33.3";
            priceStep = "0.00001";
        }

        if (symbol.equals("TRXZ18")) {
            maxLeverage = "20";
            priceStep = "0.00000001";
        }

        if (symbol.equals("XRPZ18")) {
            maxLeverage = "20";
            priceStep = "0.00000001";
        }

        if (symbol.equals("XBTKRW")) {
            maxLeverage = "100";
        }

        Map<String, String> myMap = new HashMap<>();
        myMap.put("maxLeverage", maxLeverage);
        myMap.put("priceStep", priceStep);

        return myMap;
    }

}
