package com.ntouzidis.bitmex_trader.module.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.ntouzidis.bitmex_trader.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.enumeration.OrderType;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Side;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.common.pojo.Context;
import com.ntouzidis.bitmex_trader.module.common.pojo.OrderReport;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.bitmex_trader.module.trade.service.IBitmexApiService;
import com.ntouzidis.bitmex_trader.module.trade.service.IBitmexTradeService;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import static com.ntouzidis.bitmex_trader.module.common.constants.AuthorizationConstants.TRADER;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.CLOSE;
import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.*;
import static com.ntouzidis.bitmex_trader.module.common.constants.ParamConstants.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    value = TRADER_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
@Api(tags = "Trader API")
@RequiredArgsConstructor
public class TraderController {

  private final Context context;
  private final UserService userService;
  private final IBitmexApiService bitmexService;
  private final IBitmexTradeService bitmexTradeService;

  @GetMapping("/followers")
  @PreAuthorize(TRADER)
  @ApiOperation("Read all your Followers (Trader-only)")
  public ResponseEntity<List<User>> getFollowers() {
    return ok(userService.getNonHiddenFollowers(context.getUser()));
  }

  @GetMapping("/followers/guide")
  @PreAuthorize(TRADER)
  @ApiOperation("Read all your Followers (Trader-only)")
  public ResponseEntity<User> getGuideFollower() {
    return ok(userService.getGuideFollower(context.getUserID()));
  }

  @PostMapping("/followers/{followerId}/guide")
  @PreAuthorize(TRADER)
  @ApiOperation("Set your guide follower (Trader-only)")
  public ResponseEntity<User> setGuideFollower(@PathVariable Long followerId) {
    return ok(userService.setGuideFollower(context.getUserID(), followerId));
  }

  @PostMapping("/followers/{followerId}/status")
  @PreAuthorize(TRADER)
  @ApiOperation("Enable or Disable a Follower (Trader-only)")
  public ResponseEntity<User> enableOrDisableFollower(
      @PathVariable Long followerId,
      @RequestParam(STATUS_PARAM) String status
  ) {
    User follower = userService.getFollowerOf(context.getUser(), followerId);
    userService.enableOrDisableFollowers(ImmutableList.of(follower), status);
    return ok(follower);
  }

  @PostMapping("/followers/status")
  @PreAuthorize(TRADER)
  @ApiOperation("Enable or Disable all Followers (Trader-only)")
  public ResponseEntity<List<User>> enableOrDisableAllFollowers(
      @RequestParam(STATUS_PARAM) String status
  ) {
    List<User> followers = userService.getFollowersByTrader(context.getUserID());
    userService.enableOrDisableFollowers(followers, status);
    return ok(followers);
  }

  @GetMapping("/followers/active-orders/guide")
  @PreAuthorize(TRADER)
  @ApiOperation("Read currently active orders of your guide Follower (Trader-only)")
  public ResponseEntity<List<BitmexOrder>> getActiveOrders() {
    return ok(bitmexTradeService.getGuideActiveOrders(context.getUser()));
  }

  @GetMapping("/followers/{followerId}/active-orders")
  @PreAuthorize(TRADER)
  @ApiOperation("Read currently active orders of one of your Followers by ID (Trader-only)")
  public ResponseEntity<List<BitmexOrder>> getActiveOrders(@PathVariable Long followerId) {
    return ok(bitmexService.getActiveOrders(userService.getOne(followerId)));
  }

  @GetMapping("/followers/open-positions/guide")
  @PreAuthorize(TRADER)
  @ApiOperation("Read currently open positions of your guide Follower (Trader-only)")
  public ResponseEntity<List<BitmexPosition>> getOpenPositions() {
    return ok(bitmexTradeService.getGuideOpenPositions(context.getUser()));
  }

  @GetMapping("/followers/{followerId}/open-positions")
  @PreAuthorize(TRADER)
  @ApiOperation("Read currently open positions orders of one of your Followers by ID (Trader-only)")
  public ResponseEntity<List<BitmexPosition>> getOpenPositions(@PathVariable Long followerId) {
    return ok(bitmexService.getOpenPositions(userService.getOne(followerId)));
  }

  @GetMapping("/followers/balances")
  @PreAuthorize(TRADER)
  @ApiOperation("Read all bitmex account balances of all your Followers (Trader-only)")
  public ResponseEntity<Map<String, Double>> getFollowerBalances() {
    return ok(bitmexTradeService.getBalances());
  }

  @GetMapping("/followers/{followerId}/wallet")
  @PreAuthorize(TRADER)
  @ApiOperation("Read bitmex account wallet info of one of your Followers by ID (Trader-only)")
  public ResponseEntity<Map<String, Object>> getUserBitmexWallet(@PathVariable Long followerId) {
    return ok(bitmexService.getUserWallet(userService.getOne(followerId)));
  }

  @GetMapping("/followers/{followerId}/wallet/history")
  @PreAuthorize(TRADER)
  @ApiOperation("Read bitmex account wallet history info of one of your Followers by ID (Trader-only)")
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletHistory(@PathVariable Long followerId) {
    return ok(bitmexService.getUserWalletHistory(userService.getOne(followerId))
            .stream()
            .limit(100)
            .collect(toList())
    );
  }

  @GetMapping("/followers/{followerId}/wallet/summary")
  @PreAuthorize(TRADER)
  @ApiOperation("Read bitmex account wallet summery of one of your Followers by ID (Trader-only)")
  public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletSummary(@PathVariable Long followerId) {
    return ok(bitmexService.getUserWalletSummary(userService.getOne(followerId))
            .stream()
            .limit(50)
            .collect(toList())
    );
  }

  @PostMapping("/order-all")
  @PreAuthorize(TRADER)
  @ApiOperation("Order for all your Followers (Trader-only)")
  public ResponseEntity<OrderReport> orderForAll(
          @RequestParam(SYMBOL_PARAM) Symbol symbol,
          @RequestParam(SIDE_PARAM) Side side,
          @RequestParam(ORD_TYPE_PARAM) OrderType ordType,
          @RequestParam(value = PRICE_PARAM, required = false) String price,
          @RequestParam(value = EXEC_INST_PARAM, required = false) String execInst,
          @RequestParam(value = STOP_PX_PARAM, required = false) String stopPx,
          @RequestParam(value = LEVERAGE_PARAM, required = false) String leverage,
          @RequestParam(value = PERCENTAGE_PARAM, defaultValue = "10") int percentage,
          @RequestParam(value = HIDDEN_PARAM, required = false) boolean hidden
  ) {
    final DataLeverageBuilder dataLeverageBuilder = new DataLeverageBuilder()
            .withSymbol(symbol)
            .withLeverage(leverage);

    final DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withSide(side)
            .withOrderType(ordType)
            .withPrice(price)
            .withExecInst(execInst)
            .withStopPrice(stopPx)
            .withDisplayQty(hidden ? 0 : null);

    return ok(bitmexTradeService.placeOrderForAll(context.getUser(), percentage, dataOrderBuilder, dataLeverageBuilder));
  }

  @PostMapping("/close-limit-all")
  @PreAuthorize(TRADER)
  @ApiOperation("Order with percentage for all your Followers (Trader-only)")
  public ResponseEntity<OrderReport> postOrderWithPercentage(
          @RequestParam(SYMBOL_PARAM) Symbol symbol,
          @RequestParam(SIDE_PARAM) Side side,
          @RequestParam(ORD_TYPE_PARAM) OrderType ordType,
          @RequestParam(PERCENTAGE_PARAM) int percentage,
          @RequestParam(name = PRICE_PARAM, required = false) String price,
          @RequestParam(name = EXEC_INST_PARAM, required = false) String execInst,
          @RequestParam(name = HIDDEN_PARAM, required = false) boolean hidden
  ) {
    final DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withSide(side)
            .withOrderType(ordType)
            .withPrice(price)
            .withExecInst(execInst)
            .withDisplayQty(hidden ? 0 : null);

    return ok(bitmexTradeService.postOrderWithPercentage(context.getUser(), percentage, dataOrderBuilder));
  }

  @DeleteMapping("/order")
  @PreAuthorize(TRADER)
  @ApiOperation("Cancel an order (Trader-only)")
  public ResponseEntity<?> cancelOrder(
          @RequestParam(name = CL_ORD_ID_PARAM, required = false) String clOrdID,
          @RequestParam(name = SYMBOL_PARAM, required = false) Symbol symbol
  ) {
    Preconditions.checkArgument(clOrdID != null || symbol != null,
            "Either orderID or symbol must be present");

    if (symbol != null) {
      bitmexTradeService.cancelAllOrders(context.getUser(), new DataDeleteOrderBuilder().withSymbol(symbol));
      return ok(toJsonNode(symbol));

    } else {
      bitmexTradeService.cancelOrder(context.getUser(), new DataDeleteOrderBuilder().withClientOrderId(clOrdID));
      return ok("{ \"clOrdID\": \"" + clOrdID + "\" }");
    }
  }

  @DeleteMapping("/position")
  @PreAuthorize(TRADER)
  @ApiOperation("Close a position (Trader-only)")
  public ResponseEntity<JsonNode> closePosition(
          @RequestParam(name = SYMBOL_PARAM, required = false) Symbol symbol
  ) {
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withOrderType(OrderType.Market)
            .withExecInst(CLOSE);

    bitmexTradeService.closeAllPosition(context.getUser(), dataOrderBuilder);

    return ok(toJsonNode(symbol));
  }

  @DeleteMapping("/panic")
  @PreAuthorize(TRADER)
  @ApiOperation("Panic button: cancels all orders and close all positions (Trader-only)")
  public ResponseEntity<Void> panicButton() {
    bitmexTradeService.panicButton(context.getUser());
    return ok().build();
  }


  private JsonNode toJsonNode(Symbol symbol) {
    try {
      return new ObjectMapper().readTree("{\"symbol\": \"" + symbol.name() + "\" }");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }
}
