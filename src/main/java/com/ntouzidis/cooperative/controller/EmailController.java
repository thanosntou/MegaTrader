package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/send")
    public String sendMail() {
        emailService.sendSimpleMessage("thanosntouzidis@gmail.com", "argent", "Congratulations, man. You did it !!! You just sent your first JAVA mail !!!!");
        return "management-panel";
    }
}
