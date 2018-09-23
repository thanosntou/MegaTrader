package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invest")
public class InvestController {

    @Autowired
    private MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String getInvestPage(Model model) {
        model.addAttribute("activeTraders", memberService.getAllSortedAndOrdered("username", "asc"));
        return "invest";
    }

}
