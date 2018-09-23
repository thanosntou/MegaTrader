package com.ntouzidis.cooperative.module.common;

import com.ntouzidis.cooperative.module.admin.AdminService;
import com.ntouzidis.cooperative.module.customer.CustomerService;
import com.ntouzidis.cooperative.module.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = {"", "/"})
    public String getDashboard(Model model, Principal principal) {
        Object user = null;

        if (principal != null) {
            String username = principal.getName();
            user = adminService.getByUsername(username);
            if (user == null) user = memberService.getByUsername(username);
            if (user == null) user = customerService.getOne(username);
        }


        if (user != null)
            model.addAttribute("user", user);

        return "dashboard";
    }

}
