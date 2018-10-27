package com.ntouzidis.cooperative.module.common.builder;

import java.util.Optional;

public class DataPostOrderBuilder {

    private String symbol;
    private String side;
    private String orderType;
    private String orderQty;
    private String price;
    private String execInst;
    private String stopPrice;
    private String percentage;
    private String text;

    public DataPostOrderBuilder withSymbol(String symbol) {
        if (symbol != null)
            this.symbol = "symbol=" + symbol;
        return this;
    }

    public DataPostOrderBuilder withSide(String side) {
        if (side != null)
            this.side = "&side=" + side;
        return this;
    }

    public DataPostOrderBuilder withOrderType(String orderType) {
        if (orderType != null)
            this.orderType = "&ordType=" + orderType;
        return this;
    }

    public DataPostOrderBuilder withOrderQty(String orderQty) {
        if (orderQty != null)
            this.orderQty = "&orderQty=" + orderQty;
        return this;
    }

    public DataPostOrderBuilder withPrice(String price) {
        if (price != null)
            this.price = "&price=" + price;
        return this;
    }

    public DataPostOrderBuilder withExecInst(String execInst) {
        if (execInst != null && orderType.equals("Market"))
            this.execInst = "&execInst=" + execInst;
        return this;
    }

    public DataPostOrderBuilder withStopPrice(String stopPrice) {
        if (stopPrice != null)
            this.stopPrice = "&stopPx=" + stopPrice;
        return this;
    }

    public DataPostOrderBuilder withText(String text) {
        if (text != null)
            this.text = "&text=" + text;
        return this;
    }

    public String get() {
        return Optional.ofNullable(symbol).orElse("") +
                Optional.ofNullable(side).orElse("") +
                Optional.ofNullable(orderType).orElse("") +
                Optional.ofNullable(orderQty).orElse("") +
                (orderType.equals("Market") && execInst != null? Optional.ofNullable(price).orElse(""): "") +
                Optional.ofNullable(execInst).orElse("") +
                Optional.ofNullable(stopPrice).orElse("") +
                Optional.ofNullable(text).orElse("");
    }

    public String getSymbol() {
        return this.symbol.substring(7);
    }

}
