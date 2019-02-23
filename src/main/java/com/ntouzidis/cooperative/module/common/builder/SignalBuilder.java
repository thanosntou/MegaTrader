package com.ntouzidis.cooperative.module.common.builder;

import com.ntouzidis.cooperative.module.common.enumeration.Side;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;

public class SignalBuilder {

    private Symbol symbol;
    private Side side;
    private String leverage;
    private String stopLoss;
    private String profitTrigger;


    public SignalBuilder withSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public SignalBuilder withSide(Side side) {
        this.side = side;
        return this;
    }

    public SignalBuilder withStopLoss(String stopLoss) {
        this.stopLoss = stopLoss;
        return this;
    }

    public SignalBuilder withProfitTrigger(String profitTrigger) {
        this.profitTrigger = profitTrigger;
        return this;
    }

    public SignalBuilder withleverage(String leverage) {
        this.leverage = leverage;
        return this;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Side getSide() {
        return side;
    }

    public String getLeverage() {
        return leverage;
    }

    public String getStopLoss() {
        return stopLoss;
    }

    public String getProfitTrigger() {
        return profitTrigger;
    }
}
