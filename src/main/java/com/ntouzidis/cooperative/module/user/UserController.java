package com.ntouzidis.cooperative.module.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/apikey")
    public String saveApiKeys(@RequestParam(name="apikey") String apiKey,
                              @RequestParam(name="apisecret") String apiSecret,
                              Principal p) {

        userService.saveKeys(p.getName(), apiKey, apiSecret);

        return "redirect:/dashboard";
    }

}
