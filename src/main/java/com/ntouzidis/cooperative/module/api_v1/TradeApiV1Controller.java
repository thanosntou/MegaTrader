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

@RestController
@RequestMapping("/api/v1/trade")
public class TradeApiV1Controller {

  private final Context context;
  private final TradeService tradeService;

  public TradeApiV1Controller(Context context, TradeService tradeService) {
    this.context = context;
    this.tradeService = tradeService;
  }

  @PostMapping(value = "/orderAll", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<OrderReport> postOrder(
          @RequestParam(name="symbol") Symbol symbol,
          @RequestParam(name="side") Side side,
          @RequestParam(name="ordType") OrderType ordType,
          @RequestParam(name="price", required=false) String price,
          @RequestParam(name="execInst", required=false) String execInst,
          @RequestParam(name="stopPx", required = false) String stopPx,
          @RequestParam(name="leverage", required = false) String leverage,
          @RequestParam(name="hidden", required = false) Boolean hidden,
          @RequestParam(name="percentage", required = false, defaultValue = "10") Integer percentage
  ) {
    DataLeverageBuilder dataLeverageBuilder = new DataLeverageBuilder()
            .withSymbol(symbol)
            .withLeverage(leverage);

    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withSide(side)
            .withOrderType(ordType)
            .withPrice(price)
            .withExecInst(execInst)
            .withStopPrice(stopPx)
            .withDisplayQty(hidden ? 0 : null);

    OrderReport report = tradeService.placeOrderAll(context.getUser(), dataLeverageBuilder, dataOrderBuilder, percentage);

    return ResponseEntity.ok(report);
  }

  @PostMapping(value = "/orderAll2", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<JsonNode> postOrderWithPercentage(
          @RequestParam(name = "symbol") Symbol symbol,
          @RequestParam(name = "side") Side side,
          @RequestParam(name = "ordType") OrderType ordType,
          @RequestParam(name = "percentage", required = false) int percentage,
          @RequestParam(name = "price", required = false) String price,
          @RequestParam(name = "execInst", required = false) String execInst,
          @RequestParam(name = "hidden", required = false) boolean hidden
  ) {
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withSide(side)
            .withOrderType(ordType)
            .withPrice(price)
            .withExecInst(execInst)
            .withDisplayQty(hidden ? 0 : null);

    tradeService.postOrderWithPercentage(context.getUser(), dataOrderBuilder, percentage);

    return ResponseEntity.ok(toJsonNode(symbol));
  }

  @DeleteMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<?> cancelOrder(
          @RequestParam(name="clOrdID", required = false) String clOrdID,
          @RequestParam(name="symbol", required = false) Symbol symbol
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

  @DeleteMapping(value = "/position", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<JsonNode> closePosition(
          @RequestParam(name="symbol", required = false) Symbol symbol
  ) {
    DataOrderBuilder dataOrderBuilder = new DataOrderBuilder()
            .withSymbol(symbol)
            .withOrderType(OrderType.Market)
            .withExecInst("Close");

    tradeService.closeAllPosition(context.getUser(), dataOrderBuilder);

    return ResponseEntity.ok(toJsonNode(symbol));
  }

  @DeleteMapping(value = "/panic", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('TRADER')")
  public ResponseEntity<?> panicButton() {
    tradeService.panicButton(context.getUser());
    return ResponseEntity.ok("{ \"result\": \"ok\" }");
  }

  private JsonNode toJsonNode(Symbol symbol) {
    try {
      return new ObjectMapper().readTree("{\"symbol\": \"" + symbol.name() + "\" }");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }
}
