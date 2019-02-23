package com.ntouzidis.cooperative.module.common.builder;

import com.ntouzidis.cooperative.module.common.enumeration.Symbol;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DataPostLeverage {

    private Symbol symbol;
    private String leverage;

    public DataPostLeverage withSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public DataPostLeverage withLeverage(String leverage) {
        this.leverage = leverage;
        return this;
    }

    public String get() {
        AtomicReference<String> data = new AtomicReference<>();
        Optional.ofNullable(symbol).ifPresent(i -> data.set("symbol=" + i.getValue()));
        Optional.ofNullable(leverage).ifPresent(i -> data.set(data.get() + "&leverage=" + i));
        return data.get();
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public String getLeverage() {
        return leverage;
    }
}
