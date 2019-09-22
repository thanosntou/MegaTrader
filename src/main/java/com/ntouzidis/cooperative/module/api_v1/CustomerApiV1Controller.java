package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.CUSTOMER_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.*;
import static com.ntouzidis.cooperative.module.common.RolesConstants.CUSTOMER_ROLE;
import static com.ntouzidis.cooperative.module.common.RolesConstants.TRADER_ROLE;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    value = CUSTOMER_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class CustomerApiV1Controller {

  private static final String FOLLOW_PATH = "/follow";
  private static final String PERSONAL_PATH = "/personal";
  private static final String UNFOLLOW_PATH = "/unfollow";
  private static final String OPEN_POSITIONS_PATH = "/open_positions";
  private static final String ACTIVE_ORDERS_PATH = "/active_orders";
  private static final String WALLET_PATH = "/wallet";
  private static final String WALLET_HISTORY_PATH = "/wallet/history";
  private static final String WALLET_SUMMARY_PATH = "/wallet/summary";

  private final Context context;
  private final UserService userService;
  private final BitmexService bitmexService;

  public CustomerApiV1Controller(
      Context context,
      UserService userService,
      BitmexService bitmexService
  ) {
    this.context = context;
    this.userService = userService;
    this.bitmexService = bitmexService;
  }

  @PostMapping(FOLLOW_PATH)
  @PreAuthorize(CUSTOMER_ROLE)
  public ResponseEntity<User> followTrader(@RequestParam(TRADER_ID_PARAM) Integer traderId) {
    User trader = userService.getTrader(traderId);
    userService.linkTraderOf(context.getUser(), userService.getTrader(trader.getId()));
    return ResponseEntity.ok(trader);
  }

  @GetMapping(PERSONAL_PATH)
  public ResponseEntity<User> getPersonalTrader() {
    return ResponseEntity.ok(userService.getPersonalTraderOf(context.getUser()));
  }

  @PostMapping(UNFOLLOW_PATH)
  @PreAuthorize(CUSTOMER_ROLE)
  public ResponseEntity<User> unfollowTrader() {
    userService.unlinkTraderOf(context.getUser());
    return ResponseEntity.ok(context.getUser());
  }

  @GetMapping(ACTIVE_ORDERS_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<BitmexOrder>> getActiveOrders(@RequestParam(ID_PARAM) int id) {
    return ResponseEntity.ok(bitmexService.getActiveOrders(userService.getOne(id)));
  }

  @GetMapping(OPEN_POSITIONS_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<BitmexPosition>> getOpenPositions(@RequestParam(ID_PARAM) int id) {
    return ResponseEntity.ok(bitmexService.getOpenPositions(userService.getOne(id)));
  }

  @GetMapping(WALLET_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<Map<String, Object>> getUserBitmexWallet(@RequestParam(ID_PARAM) int id) {
    return ResponseEntity.ok(bitmexService.getUserWallet(userService.getOne(id)));
  }

  @GetMapping(WALLET_HISTORY_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletHistory(@RequestParam(ID_PARAM) int id) {
    List<Map<String, Object>> walletHistory = bitmexService.getUserWalletHistory(userService.getOne(id))
        .stream()
        .limit(100)
        .collect(toList());

    return ResponseEntity.ok(walletHistory);
  }

  @GetMapping(WALLET_SUMMARY_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletSummary(@RequestParam(ID_PARAM) int id) {
    List<Map<String, Object>> walletHistory = bitmexService.getUserWalletSummary(userService.getOne(id))
        .stream()
        .limit(50)
        .collect(toList());

    return ResponseEntity.ok(walletHistory);
  }
}
