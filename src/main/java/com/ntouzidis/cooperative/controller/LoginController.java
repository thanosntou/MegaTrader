package com.ntouzidis.cooperative.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = {"", "/"})
    public String entrance() {
        return "welcome";
    }
    
    @GetMapping("/login")
    public String showMyWelcomePage(){
        return "welcome";
    }
    
    @GetMapping("/access-denied")
    public String showDeniedPage(){
        return "access-denied";
    }
}