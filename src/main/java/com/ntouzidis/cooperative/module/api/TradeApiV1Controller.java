package com.ntouzidis.cooperative.module.api;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.trade.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeApiV1Controller {

    private final TradeService tradeService;

    public TradeApiV1Controller(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(value = "/signal")
    public ResponseEntity<?> createSignal(@RequestParam(name="symbol", required = false) String symbol,
                                          @RequestParam(name="side", required = false) String side,
                                          @RequestParam(name="leverage", required = false) String leverage,
                                          @RequestParam(name="stopLoss", required = false) String stopLoss,
                                          @RequestParam(name="profitTrigger", required = false) String profitTrigger,
                                          Authentication authentication)
    {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        if (symbol == null) symbol = "XBTUSD";

        SignalBuilder signalBuilder = new SignalBuilder()
                .withSymbol(symbol)
                .withSide(side)
                .withleverage(leverage)
                .withStopLoss(stopLoss)
                .withProfitTrigger(profitTrigger);

        tradeService.createSignal(trader, signalBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @PostMapping(
            value = "/orderAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postOrderAll(@RequestParam(name="symbol") String symbol,
                                          @RequestParam(name="side") String side,
                                          @RequestParam(name="ordType") String ordType,
                                          @RequestParam(name="price", required=false) String price,
                                          @RequestParam(name="execInst", required=false) String execInst,
                                          @RequestParam(name="stopPx", required = false) String stopPx,
                                          @RequestParam(name="leverage", required = false) String leverage,
                                          Authentication authentication) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage()
                .withSymbol(symbol)
                .withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withSide(side).withOrderType(ordType)
                .withPrice(price).withExecInst(execInst).withStopPrice(stopPx);

        tradeService.placeOrderAll(trader, dataLeverageBuilder, dataOrderBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @PostMapping(
            value = "/orderAll2",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postOrder2(@RequestParam("symbol") String symbol,
                                            @RequestParam("orderType") String orderType,
                                            @RequestParam("side") String side,
                                            @RequestParam(value = "percentage", required = false) int percentage,
                                            @RequestParam(value = "price", required = false) String price,
                                            @RequestParam(value = "execInst", required = false) String execInst,
                                            Authentication authentication) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withOrderType(orderType)
                .withSide(side)
                .withPrice(price)
                .withExecInst(execInst);

        tradeService.postOrder2(trader, dataPostOrderBuilder, percentage);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/order",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> cancelOrder(@RequestParam(name="orderID", required = false) String orderID,
                                         @RequestParam(name="symbol", required = false) String symbol,
                                         Authentication authentication) {

        Preconditions.checkArgument(orderID != null || symbol != null,
                "Either orderID or symbol must be present");

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        if (symbol != null) {
            DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                    .withSymbol(symbol);

            tradeService.cancelAllOrders(trader, dataDeleteOrderBuilder);

            return new ResponseEntity<>("{ \"orderID\": \"" + orderID + "\" }", HttpStatus.OK);

        } else {
            DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                    .withOrderID(orderID);

            tradeService.cancelOrder(trader, dataDeleteOrderBuilder);

            return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
        }
    }

    @DeleteMapping(
            value = "/position",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> closePosition(@RequestParam(name="symbol", required = false) String symbol,
                                           Authentication authentication
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withOrderType("Market")
                .withExecInst("Close");

        tradeService.closeAllPosition(trader, dataPostOrderBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }
}
