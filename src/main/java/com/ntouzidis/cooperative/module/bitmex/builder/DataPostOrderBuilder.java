package com.ntouzidis.cooperative.module.bitmex.builder;

import java.util.Optional;

public class DataPostOrderBuilder {

    private String symbol;
    private String side;
    private String orderType;
    private String orderQty;
    private String price;
    private String execInst;
    private String stopPrice;

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
        if (execInst != null)
            this.execInst = "&execInst=" + execInst;
        return this;
    }

    public DataPostOrderBuilder withStopPrice(String stopPrice) {
        if (stopPrice != null)
            this.stopPrice = "&stopPrice=" + stopPrice;
        return this;
    }

    public String get() {
        return Optional.ofNullable(symbol).orElse("") +
                Optional.ofNullable(side).orElse("") +
                Optional.ofNullable(orderType).orElse("") +
                Optional.ofNullable(orderQty).orElse("") +
                Optional.ofNullable(price).orElse("") +
                Optional.ofNullable(execInst).orElse("") +
                Optional.ofNullable(stopPrice).orElse("");
    }

}
