package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerApiV1Controller {

  private final Context context;
  private final UserService userService;
  private final BitmexService bitmexService;

  public CustomerApiV1Controller(Context context, UserService userService, BitmexService bitmexService) {
    this.context = context;
    this.userService = userService;
    this.bitmexService = bitmexService;
  }

  @PostMapping(value = "/follow", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> followTrader(@RequestParam(name = "traderId") Integer traderId) {
    User trader = userService.getTrader(traderId);
    userService.linkTraderOf(context.getUser(), userService.getTrader(traderId));
    return ResponseEntity.ok(trader);
  }

  @GetMapping(value = "/personal", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> getPersonalTrader() {
    return ResponseEntity.ok(userService.getPersonalTraderOf(context.getUser()));
  }

  @PostMapping(value = "/unfollow", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> unfollowTrader() {
    userService.unlinkTraderOf(context.getUser());
    return ResponseEntity.ok(context.getUser());
  }

  @GetMapping(value = "/active_orders", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<BitmexOrder>> getActiveOrders(@RequestParam("id") int id) {
    return ResponseEntity.ok(bitmexService.getActiveOrders(userService.getOne(id)));
  }

  @GetMapping(value = "/open_positions", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<BitmexPosition>> getOpenPositions(@RequestParam("id") int id) {
    return ResponseEntity.ok(bitmexService.getOpenPositions(userService.getOne(id)));
  }

  @GetMapping(value = "/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<Map<String, Object>> getUserBitmexWallet(@RequestParam("id") int id) {
    return ResponseEntity.ok(bitmexService.getUserWallet(userService.getOne(id)));
  }

  @GetMapping(value = "/wallet/history", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletHistory(@RequestParam("id") int id) {
    return ResponseEntity.ok(
        bitmexService.getUserWalletHistory(userService.getOne(id))
            .stream()
            .limit(100)
            .collect(Collectors.toList())
    );
  }

  @GetMapping(value = "/wallet/summary", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletSummary(@RequestParam("id") int id) {
    return ResponseEntity.ok(
        bitmexService.getUserWalletSummary(userService.getOne(id))
            .stream()
            .limit(50)
            .collect(Collectors.toList())
    );
  }
}
