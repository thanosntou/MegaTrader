package com.ntouzidis.cooperative.module.trade;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invest")
public class InvestController {

    @GetMapping(value = {"", "/"})
    public String getInvestPage(Model model) {
//        model.addAttribute("activeTraders", memberService.getAllSortedAndOrdered("username", "asc"));
        return "invest";
    }

}
