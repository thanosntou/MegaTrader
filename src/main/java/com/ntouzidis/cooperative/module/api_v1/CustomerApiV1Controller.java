package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerApiV1Controller {

  private final UserService userService;

  public CustomerApiV1Controller(UserService userService) {
    this.userService = userService;
  }


  @PostMapping(
          value = "/follow",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> followTrader(@RequestParam(name = "traderId") Integer traderId,
                                           Authentication authentication
  ) {
    User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    User trader = userService.findTrader(traderId).orElseThrow(() -> new RuntimeException("Trader not found"));

    userService.linkTrader(user, trader.getId());

    return ResponseEntity.ok(trader);
  }
}
