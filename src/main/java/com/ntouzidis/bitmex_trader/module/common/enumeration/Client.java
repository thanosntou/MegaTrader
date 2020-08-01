package com.ntouzidis.bitmex_trader.module.common.enumeration;

public enum Client {

  TESTNET("https://testnet.bitmex.com"),
  BITMEX("https://www.bitmex.com");

  private final String value;

  Client(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
