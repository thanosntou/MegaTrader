package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.trade.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trader")
public class TraderApiV1Controller {

  @Value("${trader}")
  private String traderName;

  @Value("${superAdmin}")
  private String superAdmin;

  private final UserService userService;
  private final TradeService tradeService;

  public TraderApiV1Controller(UserService userService, TradeService tradeService) {
    this.userService = userService;
    this.tradeService = tradeService;
  }

  @GetMapping("/followers")
  public ResponseEntity<?> getFollowers(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    List<User> followers = userService.getFollowers(trader);

    return ResponseEntity.ok(followers);
  }

  @PostMapping(value = "/status")
  public ResponseEntity<User> enableOrDisableFollower(@RequestParam("followerId") Integer followerId,
                               Authentication authentication) {

    CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

    if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TRADER")))
      throw new RuntimeException("Sorry, you are not a trader...");

    User follower = userService.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));

    follower.setEnabled(!follower.getEnabled());

    return new ResponseEntity<>(userService.update(follower), HttpStatus.OK);
  }

  @GetMapping("/active_orders")
  public ResponseEntity<List<Map<String, Object>>> getActiveOrders(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    List<Map<String, Object>> randomActiveOrders = tradeService.getRandomActiveOrders(trader);

    return new ResponseEntity<>(randomActiveOrders, HttpStatus.OK);
  }

  @GetMapping("/active_positions")
  public ResponseEntity<List<Map<String, Object>>> getActivePositions(Authentication authentication) {

    User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    List<Map<String, Object>> randomActiveOrders = tradeService.getRandomPositions(trader);

    return new ResponseEntity<>(randomActiveOrders, HttpStatus.OK);
  }


}
