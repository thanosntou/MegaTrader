package com.ntouzidis.cooperative.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showMyWelcomePage(HttpSession session){
        return "welcome";
    }
    
    @GetMapping("/access-denied")
    public String showDeniedPage(){
        return "access-denied";
    }
}