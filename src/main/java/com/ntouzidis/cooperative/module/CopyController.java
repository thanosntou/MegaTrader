package com.ntouzidis.cooperative.module;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/copy")
public class CopyController {

    private final UserService userService;

    public CopyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public String showTraders(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        List<User> activeTraders = userService.getTraders();
        User personalTrader = userService.getPersonalTrader(user.getUsername()).orElse(null);

        model.addAttribute("activeTraders", activeTraders);
        model.addAttribute("personalTrader", personalTrader);

        return "copy-panel";
    }
}
