package com.ntouzidis.cooperative.module.common.enumeration;

public enum Symbol {

    XBTUSD("XBTUSD"),
    ETHUSD("ETHUSD"),
    ADA("ADAH19"),
    BCH("BCHH19"),
    EOS("EOSH19"),
    ETH("ETHH19"),
    LTC("LTCH19"),
    TRX("TRXH19"),
    XRP("XRPH19");

    private final String value;

    Symbol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}