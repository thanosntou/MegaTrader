package com.ntouzidis.cooperative.module.common;

import com.ntouzidis.cooperative.module.bitmex.IBitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final IBitmexService bitmexService;

    public DashboardController(UserService userService, IBitmexService bitmexService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
    }

    @GetMapping(value = {"", "/"})
    public String getDashboard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        Map<String, Object> bitmexUserWalletGet = bitmexService.get_User_Margin(user);
        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
        List<Map<String, Object>> positions = bitmexService.get_Position(user);

        List<Map<String, Object>> activeOrders = null;
        List<Map<String, Object>> openPositions = null;
        Object walletBalance = null;
        Object availableMargin = null;
        String activeBalance = null;


        if (bitmexUserWalletGet != null) {
            walletBalance = bitmexUserWalletGet.get("walletBalance");
            availableMargin = bitmexUserWalletGet.get("availableMargin");
            activeBalance = String.valueOf(Integer.parseInt(walletBalance.toString()) - Integer.parseInt(availableMargin.toString()));
        }
        if (allOrders != null) activeOrders = allOrders.stream().filter(i -> i.get("ordStatus").equals("New")).collect(Collectors.toList());
        if (positions != null) openPositions = positions.stream().filter(i -> (boolean) i.get("isOpen")).collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("page", "dashboard");
        model.addAttribute("currentClient", "testnet");
        model.addAttribute("walletBalance", walletBalance);
        model.addAttribute("earned", "0");
        model.addAttribute("availableMargin", availableMargin);
        model.addAttribute("activeBalance",activeBalance );
        model.addAttribute("openPositions", openPositions);
        model.addAttribute("activeOrders", activeOrders);

        return "dashboard";
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
