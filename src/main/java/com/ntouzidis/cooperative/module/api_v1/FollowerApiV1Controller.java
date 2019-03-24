package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user/follower")
public class FollowerApiV1Controller {

  private final UserService userService;
  private final TradeService tradeService;
  private final BitmexService bitmexService;

  public FollowerApiV1Controller(UserService userService,
                                 TradeService tradeService,
                                 BitmexService bitmexService) {
    this.userService = userService;
    this.tradeService = tradeService;
    this.bitmexService = bitmexService;
  }

  @GetMapping(
          value = "/personal",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> getPersonalTrader(Authentication authentication)
  {
    User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    User personalTrader = userService.getPersonalTrader(user.getUsername())
            .orElseThrow(() -> new RuntimeException("User don't have a personal trader"));

    return ResponseEntity.ok(personalTrader);
  }

  @PostMapping(
          value = "/unfollow",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<User> unfollowTrader(Authentication authentication)
  {
    User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    userService.unlinkTrader(user);

    return ResponseEntity.ok(user);
  }

  @GetMapping(
          value = "/active_orders",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getActiveOrders(@RequestParam("id") int id)
  {
    return ResponseEntity.ok(tradeService.getActiveOrdersOf(userService.getOne(id)));
  }

  @GetMapping(
          value = "/active_positions",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getOpenPositions(@RequestParam("id") int id)
  {
    return ResponseEntity.ok(tradeService.getPositionsOf(userService.getOne(id)));
  }

  @GetMapping(
          value = "/tx",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<?> getTX(Authentication authentication,
                                 @RequestParam(name = "id", required = false) Integer id)
  {
    User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

    if (id != null)
      user = userService.findById(id).orElseThrow(() -> new NotFoundException("user not found"));

    return ResponseEntity.ok(bitmexService.get_Order_Order(user));
  }

  @GetMapping(
          value = "/wallet",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<Map<String, Object>> getUserBitmexWallet(@RequestParam("id") int id)
  {
    return ResponseEntity.ok(bitmexService.getUserWallet(userService.getOne(id)));
  }

  @GetMapping(
          value = "/wallet/history",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletHistory(@RequestParam("id") int id)
  {
    return ResponseEntity.ok(
            bitmexService.getUserWalletHistory(userService.getOne(id))
                    .stream()
                    .limit(75)
                    .collect(Collectors.toList())
    );
  }

  @GetMapping(
          value = "/wallet/summary",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletSummary(@RequestParam("id") int id)
  {
    return ResponseEntity.ok(
            bitmexService.getUserWalletSummary(userService.getOne(id))
                    .stream()
                    .limit(50)
                    .collect(Collectors.toList())
    );
  }
}
