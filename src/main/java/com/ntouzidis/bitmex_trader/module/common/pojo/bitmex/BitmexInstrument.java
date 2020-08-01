package com.ntouzidis.bitmex_trader.module.common.pojo.bitmex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitmexInstrument {

  private String symbol;
  private String lastPrice;

  public BitmexInstrument() {
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(String lastPrice) {
    this.lastPrice = lastPrice;
  }
}
