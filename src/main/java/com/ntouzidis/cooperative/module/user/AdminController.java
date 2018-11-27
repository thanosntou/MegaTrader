package com.ntouzidis.cooperative.module.user;

import com.ntouzidis.cooperative.module.trade.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.Deposit;
import com.ntouzidis.cooperative.module.user.entity.Login;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.DepositRepository;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

    private final LoginRepository loginRepository;
    private final DepositRepository depositRepository;
    private final TradeService tradeService;
    private final UserService userService;

    @Autowired
    public AdminController(LoginRepository loginRepository, DepositRepository depositRepository,
                           TradeService tradeService, UserService userService) {
        this.loginRepository = loginRepository;
        this.depositRepository = depositRepository;
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @GetMapping(value={"", "/"})
    public String showAdminPanel(Authentication authentication, Model model,
                                 @RequestParam(value = "component", required = false) String component) {

        User admin = ((CustomUserDetails)authentication.getPrincipal()).getUser();

        model.addAttribute("user", admin);
        model.addAttribute("page", "admin");

        if ("Logins".equals(component)) {
            List<Login> logins = loginRepository.findAll();
            Collections.reverse(logins);
            model.addAttribute("logins", logins);
        }
        if ("Deposits".equals(component)) {
            List<Deposit> deposits = depositRepository.findAll();
            Collections.reverse(deposits);
            model.addAttribute("deposits", deposits);
        }
        if ("TX".equals(component)) {
            User trader = userService.findTrader("jegejo")
                    .orElse(userService.findTrader("athan")
                            .orElse(null));

            List<Map<String, Object>> tx = tradeService.getRandomTX(trader)
                    .stream()
                    .filter(i -> i.get("text").equals("Bitcoin Syndicate"))
                    .collect(Collectors.toList());

//            Collections.reverse(tx);
            model.addAttribute("tx", tx);
        }

        return "admin-panel";
    }

    @GetMapping("/logins")
    public String showLogins() {
        return "redirect:/admin?component=Logins";
    }

    @GetMapping("/deposits")
    public String showDeposits() {
        return "redirect:/admin?component=Deposits";
    }

    @GetMapping("/tx")
    public String showTX() {
        return "redirect:/admin?component=TX";
    }
}
