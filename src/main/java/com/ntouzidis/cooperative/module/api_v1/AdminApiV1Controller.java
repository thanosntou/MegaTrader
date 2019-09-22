package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.user.entity.Login;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.ADMIN_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.RolesConstants.ADMIN_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    value = ADMIN_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class AdminApiV1Controller {

  private Logger logger = LoggerFactory.getLogger(AdminApiV1Controller.class);

  private static final String LOGINS_PATH = "/logins";
  private static final String VOLUME_PATH = "/volume";
  private static final String BALANCES_PATH = "/balances";

  @Value("${trader}")
  private String traderName;

  @Value("${superAdmin}")
  private String admin;

  private final UserService userService;
  private final LoginRepository loginRepository;

  public AdminApiV1Controller(LoginRepository loginRepository,
                              UserService userService) {

    this.loginRepository = loginRepository;
    this.userService = userService;
  }

  @GetMapping(LOGINS_PATH)
  @PreAuthorize(ADMIN_ROLE)
  public ResponseEntity<List<Login>> readLogins() {
    return ResponseEntity.ok(loginRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Login::getId))
            .collect(Collectors.toList()));
  }

  @GetMapping(VOLUME_PATH)
  @PreAuthorize(ADMIN_ROLE)
  public ResponseEntity<Map<String, Double>> calculateTotalBalance() {
    User trader = userService.getTrader(traderName);
    Map<String, Double> map = new HashMap<>();
    map.put("totalVolume", userService.calculateTotalVolume(trader));
    map.put("activeVolume", userService.calculateActiveVolume(trader));
    return ResponseEntity.ok(map);
  }

  @GetMapping(BALANCES_PATH)
  @PreAuthorize(ADMIN_ROLE)
  public ResponseEntity<Map<String, Double>> getBalances() {
    return ResponseEntity.ok(userService.getFollowerBalances(userService.getTrader(traderName)));
  }
}
