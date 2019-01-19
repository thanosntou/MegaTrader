package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.SignalBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.trade.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.http.HttpStatus;
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
                                          @RequestParam(name="side") String side,
                                          @RequestParam(name="leverage", required = false) String leverage,
                                          @RequestParam(name="stopLoss", required = false) String stopLoss,
                                          @RequestParam(name="profitTrigger", required = false) String profitTrigger,
                                          Authentication authentication)
    {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        if (symbol == null) symbol = "XBTUSD";

        SignalBuilder signalBuilder = new SignalBuilder()
                .withSymbol(Symbol.valueOf(symbol).getValue())
                .withSide(side)
                .withleverage(leverage)
                .withStopLoss(stopLoss)
                .withProfitTrigger(profitTrigger);

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
                                          @RequestParam(name="leverage", required = false) String leverage,
                                          Authentication authentication) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostLeverage dataLeverageBuilder = new DataPostLeverage()
                .withSymbol(Symbol.valueOf(symbol).getValue())
                .withLeverage(leverage);

        DataPostOrderBuilder dataOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(Symbol.valueOf(symbol).getValue())
                .withSide(side).withOrderType(ordType)
                .withPrice(price).withExecInst(execInst).withStopPrice(stopPx);

        tradeService.placeOrderAll(trader, dataLeverageBuilder, dataOrderBuilder);

        return new ResponseEntity<>("okk", HttpStatus.OK);
    }

    @DeleteMapping("/order")
    public String cancelOrder(@RequestParam(name="symbol", required = false) String symbol,
                              @RequestParam(name="orderId", required = false) String orderId,
                              Authentication authentication) {

        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        if (symbol == null) {
            for (Symbol s: Symbol.values()) {
                DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                        .withSymbol(s.getValue());
                tradeService.cancelAllOrders(trader, dataDeleteOrderBuilder);
            }
        } else {
            DataDeleteOrderBuilder dataDeleteOrderBuilder = new DataDeleteOrderBuilder()
                    .withSymbol(Symbol.valueOf(symbol).getValue())
                    .withOrderID(orderId);
            tradeService.cancelAllOrders(trader, dataDeleteOrderBuilder);
        }
        return "redirect:/trade/" + symbol;
    }

    @DeleteMapping("/position")
    public ResponseEntity<?> postPosition(@RequestParam(name="symbol", required = false) String symbol,
                                          Authentication authentication)
    {
        User trader = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        DataPostOrderBuilder dataPostOrderBuilder = new DataPostOrderBuilder()
                .withSymbol(symbol)
                .withOrderType("Market")
                .withExecInst("Close");

        tradeService.closeAllPosition(trader, dataPostOrderBuilder);

        return new ResponseEntity<>("okk", HttpStatus.OK);
    }
}
