package com.ntouzidis.cooperative.module.user;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.Deposit;
import com.ntouzidis.cooperative.module.user.entity.Login;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.DepositRepository;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    private final LoginRepository loginRepository;
    private final DepositRepository depositRepository;

    @Autowired
    public AdminController(LoginRepository loginRepository, DepositRepository depositRepository) {
        this.loginRepository = loginRepository;
        this.depositRepository = depositRepository;
    }

    @GetMapping(value={"", "/"})
    public String showAdminPanel(Authentication authentication,
                                 Model model) {

        User user = ((CustomUserDetails)authentication.getPrincipal()).getUser();

        List<Login> logins = loginRepository.findAll();
        List<Deposit> deposits = depositRepository.findAll();

        Collections.reverse(logins);
        Collections.reverse(deposits);

        model.addAttribute("logins", logins);
        model.addAttribute("deposits", deposits);
        model.addAttribute("user", user);

        return "admin-panel";
    }
}
