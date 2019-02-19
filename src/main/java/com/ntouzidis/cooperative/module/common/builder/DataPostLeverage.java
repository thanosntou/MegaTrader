package com.ntouzidis.cooperative.module.common.builder;

import com.ntouzidis.cooperative.module.common.enumeration.Symbol;

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
            this.leverage = "leverage=" + leverage;
        return this;
    }

    public String get() {
        return Optional.ofNullable(symbol).orElse("") +
                Optional.ofNullable(leverage).orElse("");

    }

    public String getLeverage() {
        if (this.leverage != null)
            return this.leverage.substring(9);
        throw new RuntimeException("Leverage is null");
    }

    public Symbol getSymbol() {
        return Symbol.valueOf(symbol.substring(7,13));
    }
}
