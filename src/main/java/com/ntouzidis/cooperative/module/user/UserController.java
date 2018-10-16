package com.ntouzidis.cooperative.module.user;

import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping(value = "/link")
    public String linkTrader(@RequestParam(name = "traderId") int traderId,
                             Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);

        userService.linkTrader(user, traderId);

        return "redirect:/dashboard";
    }

    @PostMapping(value = "/unlink")
    public String unlinkTrader(Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);

        userService.unlinkTrader(user);

        return "redirect:/dashboard";
    }


    @PostMapping(value = "/apikey")
    public String saveApiKeys(@RequestParam(name="apikey", required=false) String apiKey,
                              @RequestParam(name="apisecret", required=false) String apiSecret,
                              Principal p) {

        userService.saveKeys(p.getName(), apiKey, apiSecret);

        return "redirect:/dashboard";
    }

    @PostMapping(value = "/fixedQty")
    public String setFixedQty(@RequestParam(name="fixedQty", required=false) Long qty,
                              @RequestParam(name="symbol", required=false) String symbol,
                              Principal principal) {

        userService.setFixedQty(principal.getName(), symbol, qty);

        return "redirect:/dashboard";
    }

}
