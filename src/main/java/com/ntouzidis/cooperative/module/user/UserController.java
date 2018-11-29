package com.ntouzidis.cooperative.module.user;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.mail.EmailService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.service.UserService;
import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BitmexService bitmexService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, BitmexService bitmexService, EmailService emailService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
        this.emailService = emailService;
    }

    @GetMapping("/news")
    public String showNewsPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<Map<String, Object>> announcements = bitmexService.get_Announcements(userDetails.getUser());

        String to = "thanos_nt@yahoo.gr";
//        String to = "thanos_nt@yahoo.gr";
        String subject = "News Visit";
        String text = "Someone went into the News section to read the news.";

        emailService.sendSimpleMessage(to, subject, text);

        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("page", "news");
        model.addAttribute("announcements", announcements);

        return "news-panel";
    }

    @GetMapping("/tx")
    public String showTxPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(userDetails.getUser());

        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("page", "tx");
        model.addAttribute("allOrders", allOrders);

        return "tx-panel";
    }

    @GetMapping("/wallet")
    public String showWalletPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("page", "wallet");

        return "wallet";
    }

    @GetMapping("/settings")
    public String showSettingsPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("page", "settings");

        return "settings-panel";
    }

    @PostMapping(value = "/link")
    public String linkTrader(@RequestParam(name = "traderId") int traderId,
                             Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        userService.linkTrader(userDetails.getUser(), traderId);

        return "redirect:/copy";
    }

    @PostMapping(value = "/unlink")
    public String unlinkTrader(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        userService.unlinkTrader(userDetails.getUser());

        return "redirect:/copy";
    }

    @PostMapping(value = "/apikey")
    public String saveApiKeys(@RequestParam(name="apikey", required=false) String apiKey,
                              @RequestParam(name="apisecret", required=false) String apiSecret,
                              Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        userService.saveKeys(userDetails.getUser(), apiKey, apiSecret);

        return "redirect:/user/settings";
    }

    @PostMapping(value = "/fixedQty")
    public String setFixedQty(@RequestParam(name="fixedQty", required=false) Long qty,
                              @RequestParam(name="symbol", required=false) String symbol,
                              Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (qty != null)
            userService.setFixedQty(userDetails.getUser(), symbol, qty);

        return "redirect:/user/settings";
    }

}
