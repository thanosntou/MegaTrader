package com.ntouzidis.cooperative.module.common.builder;

import java.util.Optional;

public class DataDeleteOrderBuilder {

    private String symbol;
    private String orderID;
    private String clOrdID;
    private String text = "text=Delete from Bitcoin Syndicate";

    public DataDeleteOrderBuilder withSymbol(String symbol) {
        if (symbol != null)
            this.symbol = "symbol=" + symbol + "&";
        return this;
    }

    public DataDeleteOrderBuilder withOrderID(String orderID) {
        if (orderID != null)
            this.orderID = "orderID=" + orderID + "&";
        return this;
    }

    public DataDeleteOrderBuilder withClientOrderId(String clOrdID) {
        if (clOrdID != null)
            this.clOrdID = "clOrdID=" + clOrdID + "&";
        return this;
    }

    public DataDeleteOrderBuilder withText(String text) {
        if (text != null)
            this.text = "text=" + text + "&";
        return this;
    }

    public String get() {
        return Optional.ofNullable(symbol).orElse("") +
                Optional.ofNullable(orderID).orElse("") +
                Optional.ofNullable(clOrdID).orElse("") +
                Optional.ofNullable(text).orElse("");

    }
}
