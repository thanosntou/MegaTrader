package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.trade.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

  @PostMapping(value = "/signal")
  public ResponseEntity<?> createSignal(@RequestParam(name="symbol", required = false) String symbol,
                                        @RequestParam(name="side") String side,
                                        @RequestParam(name="leverage", required = false) String leverage,
                                        @RequestParam(name="stopLoss", required = false) String stopLoss,
                                        @RequestParam(name="profitTrigger", required = false) String profitTrigger) {

    User trader = userService.findByUsername(traderName)
            .orElseGet(() -> userService.findByUsername(superAdmin)
                    .orElseThrow(() -> new RuntimeException("Trader not found")));

    if (symbol == null) symbol = "XBTUSD";

    SignalBuilder signalBuilder = new SignalBuilder()
            .withSymbol(symbol).withSide(side).withleverage(leverage)
            .withStopLoss(stopLoss).withProfitTrigger(profitTrigger);

    tradeService.createSignal(trader, signalBuilder);

    return new ResponseEntity<>("okk", HttpStatus.OK);
  }

    @PostMapping(value = "/orderAll")
    public ResponseEntity<?> postOrderAll(@RequestParam(name="symbol") String symbol,
                                          @RequestParam(name="side") String side,
                                          @RequestParam(name="ordType") String ordType,
                                          @RequestParam(name="price", required=false) String price,
                                          @RequestParam(name="execInst", required=false) String execInst,
                                          @RequestParam(name="stopPx", required = false) String stopPx,
                                          @RequestParam(name="leverage", required = false) String leverage) {

        User trader = userService.findByUsername(traderName)
                .orElseGet(() -> userService.findByUsername(superAdmin)
                        .orElseThrow(() -> new RuntimeException("Trader not found")));

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage().withSymbol(symbol).withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder().withSymbol(symbol)
                .withSide(side).withOrderType(ordType)
                .withPrice(price).withExecInst(execInst).withStopPrice(stopPx);

        tradeService.placeOrderAll(trader, dataLeverageBuilder, dataOrderBuilder);

        return new ResponseEntity<>("okk", HttpStatus.OK);
    }
}
