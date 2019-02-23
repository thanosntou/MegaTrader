package com.ntouzidis.cooperative.module.common.enumeration;

public enum OrderType {

    Market("Market"),
    Limit("Limit"),
    Stop("Stop"),
    StopLimit("StopLimit");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
