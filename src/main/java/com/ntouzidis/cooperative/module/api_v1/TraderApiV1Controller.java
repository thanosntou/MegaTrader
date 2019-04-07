package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/trader")
public class TraderApiV1Controller {

  @Value("${trader}")
  private String traderName;
  @Value("${superAdmin}")
  private String superAdmin;

  private final Context context;
  private final UserService userService;
  private final TradeService tradeService;

  public TraderApiV1Controller(Context context, UserService userService, TradeService tradeService) {
    this.context = context;
    this.userService = userService;
    this.tradeService = tradeService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<User>> readAll() {
    // temporary till choose the final business model
    List<User> activeTraders = new ArrayList<>();
    activeTraders.add(
            userService.findByUsername(traderName)
                    .orElseGet(() -> userService.findByUsername(superAdmin)
                            .orElseThrow(() -> new NotFoundException("App Trader not found"))));

    return ResponseEntity.ok(activeTraders);
  }

  @GetMapping(value = "/followers", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<User>> getFollowers() {
    List<User> followers = userService.getNonHiddenFollowers(context.getUser());
    return ResponseEntity.ok(followers);
  }

  @PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<User> enableOrDisableFollower(@RequestParam("followerId") Integer followerId) {
    User follower = userService.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
    follower.setEnabled(!follower.getEnabled());
    return ResponseEntity.ok(userService.update(follower));
  }

  @PostMapping(value = "/statusAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<User>> enableOrDisableAllFollowers(@RequestParam("status") String status) {
    List<User> followers = userService.getFollowers(context.getUser());
    if ("disable".equalsIgnoreCase(status))
      followers.forEach(follower -> {
        follower.setEnabled(false);
        userService.update(follower);
      });
    else if ("enable".equalsIgnoreCase(status))
      followers.forEach(follower -> {
        follower.setEnabled(true);
        userService.update(follower);
      });
    return ResponseEntity.ok(followers);
  }

  @GetMapping(value = "/active_orders", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<BitmexOrder>> getActiveOrders() {
    return ResponseEntity.ok(tradeService.getGuideActiveOrders(context.getUser()));
  }

  @GetMapping(value = "/open_positions", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<BitmexPosition>> getOpenPositions() {
    return ResponseEntity.ok(tradeService.getGuideOpenPositions(context.getUser()));
  }

  @GetMapping(value = "/balances", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('TRADER')")
  public ResponseEntity<Map<String, Double>> getBalances() {

    //TODO move this to service layer
    Map<String, Double> allBalances = userService.getFollowerBalances(context.getUser());
    Map<String, Double> followerBalances = new HashMap<>();

    userService.getNonHiddenFollowers(context.getUser()).forEach(follower ->
            followerBalances.put(follower.getUsername(), allBalances.get(follower.getUsername()))
    );
    return ResponseEntity.ok(followerBalances);
  }

}
