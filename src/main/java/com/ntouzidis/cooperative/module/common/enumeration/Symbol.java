package com.ntouzidis.cooperative.module.common.enumeration;

public enum Symbol {

    XBTUSD("XBTUSD"),
    ETHUSD("ETHUSD"),
    ADAXXX("ADAH19"),
    BCHXXX("BCHH19"),
    EOSXXX("EOSH19"),
    ETHXXX("ETHH19"),
    LTCXXX("LTCH19"),
    TRXXXX("TRXH19"),
    XRPXXX("XRPH19");

    private final String value;

    Symbol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}