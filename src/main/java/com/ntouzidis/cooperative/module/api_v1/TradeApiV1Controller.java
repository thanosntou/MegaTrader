package com.ntouzidis.cooperative.module.api_v1;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.OrderType;
import com.ntouzidis.cooperative.module.common.enumeration.Side;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeApiV1Controller {

    private final TradeService tradeService;
    private final UserService userService;

    public TradeApiV1Controller(TradeService tradeService, UserService userService) {
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/signal")
    public ResponseEntity<?> createSignal(
            Authentication authentication,
            @RequestParam(name="symbol", required = false) Symbol symbol,
            @RequestParam(name="side", required = false) Side side,
            @RequestParam(name="leverage", required = false) String leverage,
            @RequestParam(name="stopLoss", required = false) String stopLoss,
            @RequestParam(name="profitTrigger", required = false) String profitTrigger
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(userService.isTrader(trader));

        SignalBuilder signalBuilder = new SignalBuilder()
                .withSymbol(symbol)
                .withSide(side)
                .withleverage(leverage)
                .withStopLoss(stopLoss)
                .withProfitTrigger(profitTrigger);

        tradeService.createSignal(trader, signalBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(
            value = "/orderAll",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postOrderAll(
            Authentication authentication,
            @RequestParam(name="symbol") Symbol symbol,
            @RequestParam(name="side") Side side,
            @RequestParam(name="ordType") OrderType ordType,
            @RequestParam(name="price", required=false) String price,
            @RequestParam(name="execInst", required=false) String execInst,
            @RequestParam(name="stopPx", required = false) String stopPx,
            @RequestParam(name="leverage", required = false) String leverage
    ) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(userService.isTrader(trader));

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage()
                .withSymbol(symbol)
                .withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withSide(side)
                .withOrderType(ordType)
                .withPrice(price)
                .withExecInst(execInst)
                .withStopPrice(stopPx);

        tradeService.placeOrderAll(trader, dataLeverageBuilder, dataOrderBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @PostMapping(
            value = "/orderAll2",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postOrder2(
            @RequestParam("symbol") Symbol symbol,
            @RequestParam("orderType") OrderType orderType,
            @RequestParam("side") Side side,
            @RequestParam(value = "percentage", required = false) int percentage,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "execInst", required = false) String execInst,
            Authentication authentication
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(userService.isTrader(trader));

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
    public ResponseEntity<?> cancelOrder(
            @RequestParam(name="clOrdID", required = false) String clOrdID,
            @RequestParam(name="symbol", required = false) Symbol symbol,
            Authentication authentication
    ) {
        Preconditions.checkArgument(clOrdID != null || symbol != null,
                "Either orderID or symbol must be present");

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(userService.isTrader(trader));

        if (symbol != null) {
            DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                    .withSymbol(symbol);

            tradeService.cancelAllOrders(trader, dataDeleteOrderBuilder);

            return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);

        } else {
            DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                    .withClientOrderId(clOrdID);

            tradeService.cancelOrder(trader, dataDeleteOrderBuilder);

            return new ResponseEntity<>("{ \"clOrdID\": \"" + clOrdID + "\" }", HttpStatus.OK);
        }
    }

    @DeleteMapping(
            value = "/position",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> closePosition(
            Authentication authentication,
            @RequestParam(name="symbol", required = false) Symbol symbol
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(userService.isTrader(trader));

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withOrderType(OrderType.Market)
                .withExecInst("Close");

        tradeService.closeAllPosition(trader, dataPostOrderBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/panic",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> panicButton(Authentication authentication) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(userService.isTrader(trader));

        tradeService.panicButton(trader);

        return ResponseEntity.ok("{ \"result\": \"ok\" }");
    }
}
