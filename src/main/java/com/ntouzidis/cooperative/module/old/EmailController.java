package com.ntouzidis.cooperative.module.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/new")
    public String showForm() {
        return "email-form";
    }

    @PostMapping("/send")
    public String sendMail(
            @RequestParam(name="message") String message) {

        LinkedList<String> emails = new LinkedList<>();
//        List<Customer> customers = customerService.getSortedAndOrdered("username", "asc");
//        List<Member> members = memberService.getAllSortedAndOrdered("username", "asc");
        emails.add("thanosntouzidis@gmail.com");
//        for (Customer x:customers) {
//            emails.add(x.getEmail());
//        }
//        for (Member x:members) {
//            emails.add(x.getEmail());
//        }
        for(int i=0;i<emails.size();i++){
            emailService.sendSimpleMessage(emails.pop(), "What's up crew", message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("i am not tired");
                e.printStackTrace();
            }
        }

        return "redirect:/management-panel";
    }
}
