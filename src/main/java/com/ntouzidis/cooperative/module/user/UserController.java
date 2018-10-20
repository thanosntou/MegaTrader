package com.ntouzidis.cooperative.module.user;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
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

    @Autowired
    public UserController(UserService userService, BitmexService bitmexService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
    }

    @GetMapping("/news")
    public String showNewsPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        List<Map<String, Object>> announcements = bitmexService.get_Announcements(user);

        model.addAttribute("user", user);
        model.addAttribute("page", "news");
        model.addAttribute("announcements", announcements);

        return "news-panel";
    }

    @GetMapping("/tx")
    public String showTxPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        model.addAttribute("user", user);
        model.addAttribute("page", "tx");

        return "tx-panel";
    }

    @GetMapping("/wallet")
    public String showWalletPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        model.addAttribute("user", user);
        model.addAttribute("page", "wallet");

        return "wallet";
    }

    @GetMapping("/settings")
    public String showSettingsPage(Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        model.addAttribute("user", user);
        model.addAttribute("page", "settings");

        return "settings-panel";
    }

    @PostMapping(value = "/link")
    public String linkTrader(@RequestParam(name = "traderId") int traderId,
                             Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        userService.linkTrader(user, traderId);

        return "redirect:/copy";
    }

    @PostMapping(value = "/unlink")
    public String unlinkTrader(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        userService.unlinkTrader(user);

        return "redirect:/copy";
    }

    @PostMapping(value = "/apikey")
    public String saveApiKeys(@RequestParam(name="apikey", required=false) String apiKey,
                              @RequestParam(name="apisecret", required=false) String apiSecret,
                              Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        userService.saveKeys(user, apiKey, apiSecret);

        return "redirect:/dashboard";
    }

    @PostMapping(value = "/fixedQty")
    public String setFixedQty(@RequestParam(name="fixedQty", required=false) Long qty,
                              @RequestParam(name="symbol", required=false) String symbol,
                              Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        userService.setFixedQty(user, symbol, qty);

        return "redirect:/dashboard";
    }

}
