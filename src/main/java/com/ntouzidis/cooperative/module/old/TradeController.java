package com.ntouzidis.cooperative.module.old;

import com.ntouzidis.cooperative.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataOrderBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Side;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.lang.*;

@Controller
@RequestMapping("/trade")
public class TradeController {

//    @Value("${trader}")
//    private String traderName;

    private final UserService userService;
    private final TradeService tradeService;

    public TradeController(UserService userService, TradeService tradeService) {
        this.userService = userService;
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
        User trader = userDetails.getUser();

        List<User> followers = userService.getFollowers(trader);
        Map<String, String> priceSteps = calculatePriceSteps();

        // sumPositions + any customer position (temporary)
//        Map<String, Double> sumPositions = tradeService.calculateSumPositions(followers);

        // Sum of Fixed Customer Qty
//        Map<String, Double> sumFixedQtys = tradeService.calculateSumFixedQtys(followers);

        // random positions. for sure not empty
        List<BitmexPosition> randomPositions = tradeService.getGuideOpenPositions(trader);

        // Current Leverage
        String currentCoinLeverage = String.valueOf(randomPositions.stream()
                .filter(i -> i.getSymbol().name().equals(symbol))
                .map(BitmexPosition::getLeverage)
                .findAny().orElse(0));

        // Current Price Step
        String currentCoinPriceStep = priceSteps.entrySet().stream()
                .filter(i -> i.getKey().equals("priceStep"+symbol))
                .findAny().orElseThrow(() -> new RuntimeException("price step not found"))
                .getValue();

        // Current coin Max Leverage
        String currentCoinMaxLeverage = calculateMaxLeverages().entrySet().stream()
                .filter(i -> i.getKey().equals("maxLeverage"+symbol))
                .findAny().orElseThrow(() -> new RuntimeException("price step not found"))
                .getValue();

        // random active orders. for sure not empty
        List<BitmexOrder> randomActiveOrders = tradeService.getGuideActiveOrders(trader);

        model.addAttribute("page", "trade");
        model.addAttribute("symbol", symbol);
        model.addAttribute("user", trader);
        model.addAttribute("followers", followers);
        model.addAttribute("priceSteps", priceSteps);

        model.addAttribute("currentCoinLeverage", currentCoinLeverage);
        model.addAttribute("currentCoinMaxLeverage", currentCoinMaxLeverage);
        model.addAttribute("currentCoinPriceStep", currentCoinPriceStep);

//        model.addAttribute("sumPositions", sumPositions);
//        model.addAttribute("sumFixedQtys", sumFixedQtys);
        model.addAttribute("randomPositions", randomPositions);
        model.addAttribute("randomActiveOrders", randomActiveOrders);
        return "trade-panel2";
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/orderAll")
    public String postOrderAll(Model model, Authentication authentication,
                               @RequestParam(name="symbol") String symbol,
                               @RequestParam(name="side") String side,
                               @RequestParam(name="ordType") String ordType,
                               @RequestParam(name="price", required=false) String price,
                               @RequestParam(name="execInst", required=false) String execInst,
                               @RequestParam(name="stopPx", required = false) String stopPx,
                               @RequestParam(name="leverage", required = false) String leverage,
                               @RequestParam(name="percentage", required = false, defaultValue = "10") Integer percentage
    ) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User trader = userDetails.getUser();

        DataLeverageBuilder dataLeverageBuilder = new DataLeverageBuilder()
                .withSymbol(Symbol.valueOf(symbol))
                .withLeverage(leverage);

        DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
                .withSymbol(Symbol.valueOf(symbol))
                .withSide(Side.valueOf(side))
                .withOrderType(OrderType.valueOf(ordType))
                .withPrice(price)
                .withExecInst(execInst)
                .withStopPrice(stopPx);

        tradeService.placeOrderAll(trader, dataLeverageBuilder, dataOrderBuilder, percentage);

        model.addAttribute("user", trader);

        return "redirect:/trade/"+symbol;
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(Authentication authentication,
                              @RequestParam(name="symbol", required = false) String symbol,
                              @RequestParam(name="orderID") String orderID
    ) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User trader = userDetails.getUser();

        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                .withOrderID(orderID);

        tradeService.cancelOrder(trader, dataDeleteOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/order/cancelAll")
    public String cancelOrderAll(Authentication authentication,
                                 @RequestParam(name="symbol", required = false) String symbol
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User trader = userDetails.getUser();

        DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                .withSymbol(Symbol.valueOf(symbol));

        tradeService.cancelAllOrders(trader, dataDeleteOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    @PostMapping("/position/closeAll")
    public String closeAllPosition(Authentication authentication,
                                   @RequestParam(name="symbol") String symbol
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User trader = userDetails.getUser();

        DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
                .withSymbol(Symbol.valueOf(symbol))
                .withExecInst("Close")
                .withOrderType(OrderType.Market);

        tradeService.closeAllPosition(trader, dataOrderBuilder);

        return "redirect:/trade/" + symbol;
    }

    private Map<String, String> calculatePriceSteps() {
        Map<String, String> priceSteps = new HashMap<>();

        priceSteps.put("priceStepXBTUSD", "0.1");
        priceSteps.put("priceStepXBTJPY", "1");
        priceSteps.put("priceStepADAZ18", "0.00000001");
        priceSteps.put("priceStepBCHZ18", "0.0001");
        priceSteps.put("priceStepEOSZ18", "0.0000001");
        priceSteps.put("priceStepETHUSD", "0.01");
        priceSteps.put("priceStepLTCZ18", "0.00001");
        priceSteps.put("priceStepTRXZ18", "0.00000001");
        priceSteps.put("priceStepXRPZ18", "0.00000001");
        priceSteps.put("priceStepXBTKRW", "1");
        return priceSteps;
    }

    private Map<String, String> calculateMaxLeverages() {
        Map<String, String> maxLeverages = new HashMap<>();

        maxLeverages.put("maxLeverageXBTUSD", "100");
        maxLeverages.put("maxLeverageXBTJPY", "100");
        maxLeverages.put("maxLeverageADAZ18", "20");
        maxLeverages.put("maxLeverageBCHZ18", "20");
        maxLeverages.put("maxLeverageEOSZ18", "20");
        maxLeverages.put("maxLeverageETHUSD", "50");
        maxLeverages.put("maxLeverageLTCZ18", "33.3");
        maxLeverages.put("maxLeverageTRXZ18", "20");
        maxLeverages.put("maxLeverageXRPZ18", "20");
        maxLeverages.put("maxLeverageXBTKRW", "100");

        return maxLeverages;
    }

}
