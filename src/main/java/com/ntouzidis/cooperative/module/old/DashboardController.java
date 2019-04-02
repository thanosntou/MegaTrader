package com.ntouzidis.cooperative.module.old;

import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final BitmexService bitmexService;

    public DashboardController(UserService userService, BitmexService bitmexService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
    }

    @GetMapping(value = {"", "/"})
    public String getDashboard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        List<Map<String, Object>> activeOrders;
        List<BitmexPosition> openPositions;
        Object walletBalance = null;
        Object availableMargin = null;
        String activeBalance = null;

        Map<String, Object> bitmexUserWalletGet = bitmexService.get_User_Margin(user);
        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
        List<BitmexPosition> positions = bitmexService.getAllPositions(user);

        if (!bitmexUserWalletGet.isEmpty()) {
            walletBalance = bitmexUserWalletGet.get("walletBalance");
            availableMargin = bitmexUserWalletGet.get("availableMargin");
            activeBalance = String.valueOf(Integer.parseInt(walletBalance.toString()) - Integer.parseInt(availableMargin.toString()));
        }
        activeOrders = allOrders.stream().filter(i -> i.get("ordStatus").equals("New")).collect(Collectors.toList());
        openPositions = positions.stream().filter(BitmexPosition::isOpen).collect(Collectors.toList());

        model.addAttribute("user", user);
//        model.addAttribute("symbol", user);
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
}
