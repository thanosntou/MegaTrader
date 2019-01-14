package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.trade.TradeService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<?> getFollowers() {

    User trader = userService.findByUsername(traderName)
            .orElseThrow(() -> new RuntimeException("Trader not found"));

    List<User> followers = userService.getFollowers(trader);

    return ResponseEntity.ok(followers);
  }

  @GetMapping("/active_orders")
  public ResponseEntity<List<Map<String, Object>>> getActiveOrders() {

    User trader = userService.findByUsername(traderName)
            .orElseGet(() -> userService.findByUsername(superAdmin)
                    .orElseThrow(() -> new RuntimeException("Trader not found")));

    List<Map<String, Object>> randomActiveOrders = tradeService.getRandomActiveOrders(trader);

    return new ResponseEntity<>(randomActiveOrders, HttpStatus.OK);
  }

  @GetMapping("/active_positions")
  public ResponseEntity<List<Map<String, Object>>> getActivePositions() {

    User trader = userService.findByUsername(traderName)
            .orElseGet(() -> userService.findByUsername(superAdmin)
                    .orElseThrow(() -> new RuntimeException("Trader not found")));

    List<Map<String, Object>> randomActiveOrders = tradeService.getRandomPositions(trader);

    return new ResponseEntity<>(randomActiveOrders, HttpStatus.OK);
  }
}
