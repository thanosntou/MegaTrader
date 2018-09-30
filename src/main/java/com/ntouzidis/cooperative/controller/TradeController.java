package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.user.User;
import com.ntouzidis.cooperative.module.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired private UserService userService;
    @Autowired private BitmexService bitmexService;

    @GetMapping(value = {"", "/"})
    public String showDefault() {
        return "redirect:/trade/XBTUSD";
    }


    @GetMapping("/{symbol}")
    public String showProducts(@PathVariable(name = "symbol") String symbol,
//                               @RequestParam(name="client", required=false, defaultValue = "testnet") String client,
                               Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        List<Map<String, Object>> positions= bitmexService.get_Order_Order(principal.getName(), "testnet");

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

        setInfos(symbol, maxLeverage, priceStep);

        model.addAttribute("usernamePrincipal", user.getUsername());
        model.addAttribute("symbol", symbol);
        model.addAttribute("maxLeverage", maxLeverage);
        model.addAttribute("priceStep", priceStep);
        model.addAttribute("positions", positions);

        return "trade-panel";
    }

    @PostMapping(value = "/order")
    public String postOrder(@RequestParam(name="client", required=false, defaultValue = "bitmex") String client,
                            @RequestParam(name="symbol") String symbol,
                            @RequestParam(name="side") String side,
                            @RequestParam(name="ordType") String ordType,
                            @RequestParam(name="orderQty") String orderQty,
                            @RequestParam(name="price", required=false) String price,
                            @RequestParam(name="execInst", required=false) String execInst,
                            @RequestParam(name="stopPx", required = false) String stopPx,
                            @RequestParam(name="leverage", required = false) String leverage,
                            Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        String dataLeverage = "";
        if (symbol != null) dataLeverage += "symbol=" + symbol;
        if (side != null) dataLeverage += "&leverage=" + leverage;

        String dataOrder = "";
        if (symbol != null) dataOrder += "symbol=" + symbol;
        if (side != null) dataOrder += "&side=" + side;
        if (ordType != null) dataOrder += "&ordType=" + ordType;
        if (orderQty != null) dataOrder += "&orderQty=" + orderQty;
        if (price != null) dataOrder += "&price=" + price;
        if (execInst != null) dataOrder += "&execInst=" + execInst;
        if (stopPx != null) dataOrder += "&stopPx=" + stopPx;


        bitmexService.post_Position_Leverage(principal.getName(), client, dataLeverage);

        bitmexService.post_Order_Order(principal.getName(), client, dataOrder);

        model.addAttribute("user", user);
        model.addAttribute("user", principal.getName());

        return "redirect:/trade/"+symbol;
    }

    private void setInfos(String symbol, String maxLeverage, String priceStep) {

    }
}
