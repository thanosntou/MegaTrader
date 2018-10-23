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
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.lang.*;

@Controller
@RequestMapping("/trade")
public class TradeController {

    private final IBitmexService bitmexService;
    private final UserService userService;
    private final TradeService tradeService;

    @Autowired
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

        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
        List<Map<String, Object>> positions = bitmexService.get_Position(user);
        List<User> followers = userService.getFollowers(user);
        List<Map<String, Object>> activeOrders = null;
        String currentLeverage = String.valueOf(positions.stream().filter(i -> i.get("symbol").equals(symbol)).map(i -> i.get("leverage")).findAny().orElse(null));

        long sumXBTUSD = followers.stream().mapToLong(i -> i.getFixedQtyXBTUSD().intValue()).sum();
        long sumXBTJPY = followers.stream().mapToLong(i -> i.getFixedQtyXBTJPY().intValue()).sum();
        long sumADAZ18 = followers.stream().mapToLong(i -> i.getFixedQtyADAZ18().intValue()).sum();
        long sumBCHZ18 = followers.stream().mapToLong(i -> i.getFixedQtyBCHZ18().intValue()).sum();
        long sumEOSZ18 = followers.stream().mapToLong(i -> i.getFixedQtyEOSZ18().intValue()).sum();
        long sumETHUSD = followers.stream().mapToLong(i -> i.getFixedQtyETHUSD().intValue()).sum();
        long sumLTCZ18 = followers.stream().mapToLong(i -> i.getFixedQtyLTCZ18().intValue()).sum();
        long sumTRXZ18 = followers.stream().mapToLong(i -> i.getFixedQtyTRXZ18().intValue()).sum();
        long sumXRPZ18 = followers.stream().mapToLong(i -> i.getFixedQtyXRPZ18().intValue()).sum();
        long sumXBTKRW = followers.stream().mapToLong(i -> i.getFixedQtyXBTKRW().intValue()).sum();

        if (allOrders != null) activeOrders = bitmexService.get_Order_Order(user).stream().filter(map -> map.get("ordStatus").equals("New")).collect(Collectors.toList());

        String maxLeverage = "0";
        String priceStep = "1";

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

        model.addAttribute("user", user);
        model.addAttribute("symbol", symbol);
        model.addAttribute("maxLeverage", maxLeverage);
        model.addAttribute("priceStep", priceStep);
        model.addAttribute("activeOrders", activeOrders);
        model.addAttribute("positions", positions);
        model.addAttribute("followers", followers);
        model.addAttribute("sumXBTUSD", sumXBTUSD);
        model.addAttribute("sumXBTJPY", sumXBTJPY);
        model.addAttribute("sumADAZ18", sumADAZ18);
        model.addAttribute("sumBCHZ18", sumBCHZ18);
        model.addAttribute("sumEOSZ18", sumEOSZ18);
        model.addAttribute("sumETHUSD", sumETHUSD);
        model.addAttribute("sumLTCZ18", sumLTCZ18);
        model.addAttribute("sumTRXZ18", sumTRXZ18);
        model.addAttribute("sumXRPZ18", sumXRPZ18);
        model.addAttribute("sumXBTKRW", sumXBTKRW);
        model.addAttribute("currentLeverage", currentLeverage);

        model.addAttribute("page", "trade");

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

    @PostMapping("/position/market")
    public String marketPosition(@RequestParam(name="symbol") String symbol,
                                 Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withExecInst("Close")
                .withOrderType("Market")
                .withText("Position close from Bitcoin Syndicate");

        tradeService.marketPosition(user, dataPostOrderBuilder);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/position/close")
    public String closePosition(@RequestParam(name="symbol") String symbol,
                                @RequestParam(name="limitPrice") String price,
                                Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withExecInst("Close")
                .withOrderType("Limit")
                .withPrice(price)
                .withText("Position close from Bitcoin Syndicate");

        tradeService.closePosition(user, dataPostOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping(value = "/order")
    public String postOrder(@RequestParam(name="symbol") String symbol,
                            @RequestParam(name="side") String side,
                            @RequestParam(name="ordType") String ordType,
                            @RequestParam(name="orderQty", required=false) String orderQty,
                            @RequestParam(name="price", required=false) String price,
                            @RequestParam(name="execInst", required=false) String execInst,
                            @RequestParam(name="stopPx", required = false) String stopPx,
                            @RequestParam(name="leverage", required = false) String leverage,
                            Model model, Principal principal) {

        User trader = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage().withSymbol(symbol).withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder().withSymbol(symbol)
                .withSide(side).withOrderType(ordType).withOrderQty(orderQty)
                .withPrice(price).withExecInst(execInst).withStopPrice(stopPx);


        tradeService.placeOrderForCustomers(trader, dataLeverageBuilder, dataOrderBuilder);

        model.addAttribute("user", trader);
        model.addAttribute("user", principal.getName());

        return "redirect:/trade/"+symbol;
    }
}
