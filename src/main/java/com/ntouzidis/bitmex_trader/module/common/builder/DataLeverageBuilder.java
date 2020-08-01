package com.ntouzidis.bitmex_trader.module.common.builder;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DataLeverageBuilder implements DataBuilder {

  private Symbol symbol;
  private String leverage;

  public DataLeverageBuilder withSymbol(Symbol symbol) {
    this.symbol = symbol;
    return this;
  }

  public DataLeverageBuilder withLeverage(String leverage) {
    this.leverage = leverage;
    return this;
  }

  @Override
  public String get() {
    AtomicReference<String> data = new AtomicReference<>();
    Optional.ofNullable(symbol).ifPresent(i -> data.set("symbol=" + i.name()));
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
