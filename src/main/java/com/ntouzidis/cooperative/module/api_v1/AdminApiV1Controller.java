package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.user.entity.Login;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminApiV1Controller {

    private final LoginRepository loginRepository;
    private final UserService userService;

    public AdminApiV1Controller(
            LoginRepository loginRepository,
            UserService userService
    ) {
        this.loginRepository = loginRepository;
        this.userService = userService;
    }

    @GetMapping(
            value = "/logins",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Login>> readLogins() {
        List<Login> logins = loginRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Login::getId))
                .collect(Collectors.toList());

        return new ResponseEntity<>(logins, HttpStatus.OK);
    }

    @GetMapping(
            value = "/volume",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('TRADER', 'ADMIN')")
    public ResponseEntity<Map<String, Double>> calculateTotalBalance() {
        double totalVolume = userService.calculateTotalVolume();
        double activeVolume = userService.calculateActiveVolume();

        Map<String, Double> map = new HashMap<>();
        map.put("totalVolume", totalVolume);
        map.put("activeVolume", activeVolume);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(
            value = "/balances",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('TRADER', 'ADMIN')")
    public ResponseEntity<Map<String, Double>> getBalances() {
        return new ResponseEntity<>(userService.getBalances(), HttpStatus.OK);
    }
}
