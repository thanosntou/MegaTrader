package com.ntouzidis.cooperative.module.trade;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.bitmex.IBitmexService;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trade")
public class TradeController {

    private final IBitmexService bitmexService;
    private final TradeService tradeService;

    @Autowired
    public TradeController(BitmexService bitmexService, TradeService tradeService) {
        this.bitmexService = bitmexService;
        this.tradeService = tradeService;
    }

    @GetMapping(value = {"", "/"})
    public String showDefault() {
        return "redirect:/trade/XBTUSD";
    }

    @GetMapping("/{symbol}")
    public String showProducts(@PathVariable(name = "symbol") String symbol,
                               Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        List<Map<String, Object>> positions = bitmexService.get_Position(user);
        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
        List<Map<String, Object>> activeOrders = null;
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

        model.addAttribute("page", "trade");

        return "trade-panel2";
    }

    @PostMapping(value = "/signal")
    public String createSignal(@RequestParam(name="symbol", required = false) String symbol,
                               @RequestParam(name="side") String side,
                               @RequestParam(name="leverage", required = false) String leverage,
                               @RequestParam(name="stopLoss", required = false) String stopLoss,
                               @RequestParam(name="profitTrigger", required = false) String profitTrigger,
                               Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

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
                              @RequestParam(name="orderID") String orderID, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                .withOrderID(orderID)
                .withText("Cancel from Bitcoin Syndicate");

        tradeService.cancelOrder(user, dataDeleteOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/order/cancelAll")
    public String cancelOrder(@RequestParam(name="symbol", required = false) String symbol,
                              Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                .withText("Canceled All from Bitcoin Syndicate");

        tradeService.cancelAllOrders(user, dataDeleteOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/position/market")
    public String marketPosition(@RequestParam(name="symbol") String symbol, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withExecInst("Close")
                .withOrderType("Market")
                .withText("Position close from Bitcoin Syndicate");

        tradeService.marketPosition(user, dataPostOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/position/close")
    public String closePosition(@RequestParam(name="symbol") String symbol,
                                @RequestParam(name="limitPrice") String price,
                                Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withExecInst("Close")
                .withOrderType("Limit")
                .withPrice(price)
                .withText("Porition close from Bitcoin Syndicate");

        tradeService.closePosition(user, dataPostOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

//    @PostMapping(value = "/order")
//    public String postOrder(@RequestParam(name="client", required=false, defaultValue = "bitmex") String client,
//                            @RequestParam(name="symbol") String symbol,
//                            @RequestParam(name="side") String side,
//                            @RequestParam(name="ordType") String ordType,
//                            @RequestParam(name="orderQty") String orderQty,
//                            @RequestParam(name="price", required=false) String price,
//                            @RequestParam(name="execInst", required=false) String execInst,
//                            @RequestParam(name="stopPx", required = false) String stopPx,
//                            @RequestParam(name="leverage", required = false) String leverage,
//                            Model model, Principal principal) {
//
//        User trader = userService.findTrader(principal.getName()).orElseThrow(RuntimeException::new);
//
//        DataPostLeverage dataLeverageBuilder = new DataPostLeverage().withSymbol(symbol).withLeverage(leverage);
//
//        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder().withSymbol(symbol)
//                .withSide(side).withOrderType(ordType).withOrderQty(orderQty)
//                .withPrice(price).withExecInst(execInst).withStopPrice(stopPx);
//
//
//        tradeService.placeOrderForCustomers(trader.getUsername(), dataLeverageBuilder, dataOrderBuilder);
//
//        model.addAttribute("user", trader);
//        model.addAttribute("user", principal.getName());
//
//        return "redirect:/trade/"+symbol;
//    }
}
