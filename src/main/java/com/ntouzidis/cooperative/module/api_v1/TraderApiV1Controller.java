package com.ntouzidis.cooperative.module.api_v1;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trader")
public class TraderApiV1Controller {

  @Value("${trader}")
  private String traderName;

  @Value("${superAdmin}")
  private String superAdmin;

  private final UserService userService;
  private final TradeService tradeService;
  private final PasswordEncoder passwordEncoder;

  public TraderApiV1Controller(UserService userService,
                               TradeService tradeService,
                               PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.tradeService = tradeService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping(
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> readAll(Authentication authentication) {

    User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    // temporary till choose the final business model
    List<User> activeTraders = new ArrayList<>();
    activeTraders.add(
            userService.findByUsername(traderName).orElseGet(() ->
                    userService.findByUsername(superAdmin).orElseThrow(() ->
                            new NotFoundException("App Trader not found")))
    );

    return ResponseEntity.ok(activeTraders);
  }

  @GetMapping(
          value = "/followers",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<?> getFollowers(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    Preconditions.checkArgument(userService.isTrader(trader));

    List<User> followers = userService.getFollowers(trader);

    return ResponseEntity.ok(followers);
  }

  @PostMapping(
          value = "/status",
          produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<User> enableOrDisableFollower(
          Authentication authentication,
          @RequestParam("followerId") Integer followerId
  ) {

    CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

    Preconditions.checkArgument(userService.isTrader(userDetails.getUser()));

    if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRADER")))
      throw new RuntimeException("Sorry, you are not a trader...");

    User follower = userService.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));

    follower.setEnabled(!follower.getEnabled());

    return new ResponseEntity<>(userService.update(follower), HttpStatus.OK);
  }

  @PostMapping(
          value = "/statusAll",
          produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<User>> enableOrDisableAllFollowers(
          Authentication authentication,
          @RequestParam("status") String status
  ) {

    CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

    if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRADER")))
      throw new RuntimeException("Sorry, you are not a trader...");

    List<User> followers = userService.getFollowers(userDetails.getUser());

    if ("disable".equalsIgnoreCase(status)) {
      followers.forEach(follower -> {
        follower.setEnabled(false);
        userService.update(follower);
      });
    }
    else if ("enable".equalsIgnoreCase(status)) {
      followers.forEach(follower -> {
        follower.setEnabled(true);
        userService.update(follower);
      });
    }

    return new ResponseEntity<>(followers, HttpStatus.OK);
  }

  @GetMapping(
          value = "/active_orders",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getActiveOrders(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    Preconditions.checkArgument(userService.isTrader(trader));

    List<Map<String, Object>> randomActiveOrders = tradeService.getRandomActiveOrders(trader);

    return new ResponseEntity<>(randomActiveOrders, HttpStatus.OK);
  }

  @GetMapping(
          value = "/active_positions",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getOpenPositions(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    Preconditions.checkArgument(userService.isTrader(trader));

    List<Map<String, Object>> randomOpenPositions = tradeService.getRandomPositions(trader)
            .stream()
            .filter(pos -> Arrays.stream(Symbol.values())
                    .map(Symbol::name)
                    .collect(Collectors.toList())
                    .contains(pos.get("symbol").toString()))
            .filter(pos -> pos.get("avgEntryPrice") != null)
            .collect(Collectors.toList());

    return new ResponseEntity<>(randomOpenPositions, HttpStatus.OK);
  }

  @GetMapping(
          value = "/balances",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasAnyRole('TRADER')")
  public ResponseEntity<Map<String, Double>> getBalances(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();


    return new ResponseEntity<>(userService.getBalances(), HttpStatus.OK);
  }

}
