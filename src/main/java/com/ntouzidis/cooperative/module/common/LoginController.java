package com.ntouzidis.cooperative.module.common;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @GetMapping(value = "/proxy")
    public String selectLoginPage(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRADER")))
            return "redirect:/trade";
        else
            return "redirect:/dashboard";
    }
}