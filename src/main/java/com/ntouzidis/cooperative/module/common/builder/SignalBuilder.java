package com.ntouzidis.cooperative.module.common.builder;

public class SignalBuilder {

    private String symbol;
    private String side;
    private String leverage;
    private String stopLoss;
    private String profitTrigger;


    public SignalBuilder withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public SignalBuilder withSide(String side) {
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

    public String getSymbol() {
        return symbol;
    }

    public String getSide() {
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
