package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.user.entity.Login;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminApiV1Controller {

    private final LoginRepository loginRepository;


    public AdminApiV1Controller(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @GetMapping("/logins")
    public ResponseEntity<List<Login>> readLogins()
    {
        List<Login> logins = loginRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Login::getId))
                .collect(Collectors.toList());

        return new ResponseEntity<>(logins, HttpStatus.OK);
    }
}
