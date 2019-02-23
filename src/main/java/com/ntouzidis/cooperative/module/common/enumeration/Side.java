package com.ntouzidis.cooperative.module.common.enumeration;

public enum Side {

    Buy("Buy"),
    Sell("Sell");

    private final String value;

    Side(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
