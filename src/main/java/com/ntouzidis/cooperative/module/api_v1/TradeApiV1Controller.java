package com.ntouzidis.cooperative.module.api_v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataOrderBuilder;
import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Side;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.pojo.OrderReport;
import com.ntouzidis.cooperative.module.service.TradeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.TRADE_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.*;
import static com.ntouzidis.cooperative.module.common.RolesConstants.TRADER_ROLE;

@RestController
@RequestMapping(
    value = TRADE_CONTROLLER_PATH,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class TradeApiV1Controller {

  private static final String PANIC_PATH = "/panic";
  private static final String POSITION_PATH = "/position";
  private static final String ORDER_PATH = "/order";
  private static final String ORDER_ALL_PATH = "/orderAll";
  private static final String ORDER_ALL2_PATH = "/orderAll2";

  private final Context context;
  private final TradeService tradeService;

  public TradeApiV1Controller(Context context, TradeService tradeService) {
    this.context = context;
    this.tradeService = tradeService;
  }

  @PostMapping(ORDER_ALL_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<OrderReport> postOrder(
          @RequestParam(SYMBOL_PARAM) Symbol symbol,
          @RequestParam(SIDE_PARAM) Side side,
          @RequestParam(ORD_TYPE_PARAM) OrderType ordType,
          @RequestParam(value = PRICE_PARAM, required=false) String price,
          @RequestParam(value = EXEC_INST_PARAM, required=false) String execInst,
          @RequestParam(value = STOP_PX_PARAM, required = false) String stopPx,
          @RequestParam(value = LEVERAGE_PARAM, required = false) String leverage,
          @RequestParam(value = HIDDEN_PARAM, required = false) boolean hidden,
          @RequestParam(value = PERCENTAGE_PARAM, defaultValue = "10") Integer percentage
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

    return ResponseEntity.ok(tradeService.placeOrderForAll(context.getUser(), dataLeverageBuilder, dataOrderBuilder, percentage));
  }

  @PostMapping(ORDER_ALL2_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<OrderReport> postOrderWithPercentage(
          @RequestParam(SYMBOL_PARAM) Symbol symbol,
          @RequestParam(SIDE_PARAM) Side side,
          @RequestParam(ORD_TYPE_PARAM) OrderType ordType,
          @RequestParam(name = PERCENTAGE_PARAM, required = false) int percentage,
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

    return ResponseEntity.ok(tradeService.postOrderWithPercentage(context.getUser(), dataOrderBuilder, percentage));
  }

  @DeleteMapping(ORDER_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<?> cancelOrder(@RequestParam(name = CL_ORD_ID_PARAM, required = false) String clOrdID,
                                       @RequestParam(name = SYMBOL_PARAM, required = false) Symbol symbol
  ) {
    Preconditions.checkArgument(clOrdID != null || symbol != null,
            "Either orderID or symbol must be present");
    if (symbol != null) {
      tradeService.cancelAllOrders(context.getUser(), new DataDeleteOrderBuilder().withSymbol(symbol));
      return ResponseEntity.ok(toJsonNode(symbol));

    } else {
      tradeService.cancelOrder(context.getUser(), new DataDeleteOrderBuilder().withClientOrderId(clOrdID));
      return ResponseEntity.ok("{ \"clOrdID\": \"" + clOrdID + "\" }");
    }
  }

  @DeleteMapping(POSITION_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<JsonNode> closePosition(@RequestParam(name = SYMBOL_PARAM, required = false) Symbol symbol) {
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withOrderType(OrderType.Market)
            .withExecInst("Close");

    tradeService.closeAllPosition(context.getUser(), dataOrderBuilder);

    return ResponseEntity.ok(toJsonNode(symbol));
  }

  @DeleteMapping(PANIC_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity panicButton() {
    tradeService.panicButton(context.getUser());
    return ResponseEntity.ok().build();
  }

  private JsonNode toJsonNode(Symbol symbol) {
    try {
      return new ObjectMapper().readTree("{\"symbol\": \"" + symbol.name() + "\" }");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }
}
