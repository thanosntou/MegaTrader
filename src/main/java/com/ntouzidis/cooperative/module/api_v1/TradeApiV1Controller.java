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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeApiV1Controller {

    private final TradeService tradeService;

    public TradeApiV1Controller(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping(
            value = "/signal",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> createSignal(
            Authentication authentication,
            @RequestParam(name="symbol", required = false) Symbol symbol,
            @RequestParam(name="side", required = false) Side side,
            @RequestParam(name="leverage", required = false) String leverage,
            @RequestParam(name="stopLoss", required = false) String stopLoss,
            @RequestParam(name="profitTrigger", required = false) String profitTrigger
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

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
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> postOrderAll(
            Authentication authentication,
            @RequestParam(name="symbol") Symbol symbol,
            @RequestParam(name="side") Side side,
            @RequestParam(name="ordType") OrderType ordType,
            @RequestParam(name="hidden", required = false) boolean hidden,
            @RequestParam(name="price", required=false) String price,
            @RequestParam(name="execInst", required=false) String execInst,
            @RequestParam(name="stopPx", required = false) String stopPx,
            @RequestParam(name="leverage", required = false) String leverage
    ) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage()
                .withSymbol(symbol)
                .withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withSide(side)
                .withOrderType(ordType)
                .withPrice(price)
                .withExecInst(execInst)
                .withStopPrice(stopPx)
                .withDisplayQty(hidden ? 0 : null);

        tradeService.placeOrderAll(trader, dataLeverageBuilder, dataOrderBuilder);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @PostMapping(
            value = "/orderAll2",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> postOrder2(
            Authentication authentication,
            @RequestParam("symbol") Symbol symbol,
            @RequestParam("side") Side side,
            @RequestParam("orderType") OrderType orderType,
            @RequestParam(value = "percentage", required = false) int percentage,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "execInst", required = false) String execInst
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withSide(side)
                .withOrderType(orderType)
                .withPrice(price)
                .withExecInst(execInst);

        tradeService.postOrderWithPercentage(trader, dataPostOrderBuilder, percentage);

        return new ResponseEntity<>("{ \"symbol\": \"" + symbol + "\" }", HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/order",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> cancelOrder(
            Authentication authentication,
            @RequestParam(name="clOrdID", required = false) String clOrdID,
            @RequestParam(name="symbol", required = false) Symbol symbol
    ) {
        Preconditions.checkArgument(clOrdID != null || symbol != null,
                "Either orderID or symbol must be present");

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

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
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> closePosition(
            Authentication authentication,
            @RequestParam(name="symbol", required = false) Symbol symbol
    ) {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

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
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> panicButton(Authentication authentication) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        tradeService.panicButton(trader);

        return ResponseEntity.ok("{ \"result\": \"ok\" }");
    }
}
