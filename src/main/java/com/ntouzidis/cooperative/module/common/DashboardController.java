package com.ntouzidis.cooperative.module.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping(value = {"", "/"})
    public String getDashboard() {

        return "dashboard";
    }

}
