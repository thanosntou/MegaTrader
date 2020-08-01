package com.ntouzidis.bitmex_trader.module.common.enumeration;

public enum Symbol {

  XBTUSD("XBTUSD"),
  ETHUSD("ETHUSD"),
  ADAXXX("ADAM19"),
  BCHXXX("BCHM19"),
  EOSXXX("EOSM19"),
  ETHXXX("ETHM19"),
  LTCXXX("LTCM19"),
  TRXXXX("TRXM19"),
  XRPXXX("XRPM19");

  private String value;

  Symbol(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}