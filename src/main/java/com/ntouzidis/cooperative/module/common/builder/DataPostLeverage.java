package com.ntouzidis.cooperative.module.common.builder;

import java.util.Optional;

public class DataPostLeverage {

    private String symbol;
    private String leverage;

    public DataPostLeverage withSymbol(String symbol) {
        if (symbol != null)
            this.symbol = "symbol=" + symbol + "&";
        return this;
    }

    public DataPostLeverage withLeverage(String leverage) {
        if (leverage != null)
            this.leverage = "leverage=" + leverage + "&";
        return this;
    }

    public String get() {
        return Optional.ofNullable(symbol).orElse("") +
                Optional.ofNullable(leverage).orElse("");

    }
}
