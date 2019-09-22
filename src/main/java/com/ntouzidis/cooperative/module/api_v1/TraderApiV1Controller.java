package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.TRADER_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.FOLLOWER_ID_PARAM;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.STATUS_PARAM;
import static com.ntouzidis.cooperative.module.common.RolesConstants.TRADER_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    value = TRADER_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class TraderApiV1Controller {

  private static final String FOLLOWERS_PATH = "/followers";
  private static final String STATUS_PATH = "/status";
  private static final String BALANCES_PATH = "/balances";
  private static final String STATUS_ALL_PATH = "/statusAll";
  private static final String ACTIVE_ORDERS_PATH = "/active_orders";
  private static final String OPEN_POSITIONS_PATH = "/open_positions";

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

  @GetMapping
  public ResponseEntity<List<User>> readAll() {
    // temporary till choose the final business model
    List<User> activeTraders = new ArrayList<>();
    activeTraders.add(userService.getOne(traderName));

    return ResponseEntity.ok(activeTraders);
  }

  @GetMapping(FOLLOWERS_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<User>> getFollowers() {
    List<User> followers = userService.getNonHiddenFollowers(context.getUser());
    return ResponseEntity.ok(followers);
  }

  @PostMapping(STATUS_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<User> enableOrDisableFollower(
      @RequestParam(FOLLOWER_ID_PARAM) Integer followerId
  ) {
    User follower = userService.getOne(followerId);
    follower.setEnabled(!follower.getEnabled());
    return ResponseEntity.ok(userService.update(follower));
  }

  @PostMapping(STATUS_ALL_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<User>> enableOrDisableAllFollowers(
      @RequestParam(STATUS_PARAM) String status
  ) {
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

  @GetMapping(ACTIVE_ORDERS_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<BitmexOrder>> getActiveOrders() {
    return ResponseEntity.ok(tradeService.getGuideActiveOrders(context.getUser()));
  }

  @GetMapping(OPEN_POSITIONS_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<BitmexPosition>> getOpenPositions() {
    return ResponseEntity.ok(tradeService.getGuideOpenPositions(context.getUser()));
  }

  @GetMapping(BALANCES_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<Map<String, Double>> getBalances() {

    //TODO move this to service layer
    Map<String, Double> allBalances = userService.getFollowerBalances(context.getUser());
    Map<String, Double> followerBalances = new HashMap<>();

    userService.getNonHiddenFollowers(context.getUser())
        .forEach(follower -> followerBalances.put(follower.getUsername(), allBalances.get(follower.getUsername())));

    return ResponseEntity.ok(followerBalances);
  }

}
