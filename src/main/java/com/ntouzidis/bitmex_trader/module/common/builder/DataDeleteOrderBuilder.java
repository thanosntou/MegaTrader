package com.ntouzidis.bitmex_trader.module.common.builder;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DataDeleteOrderBuilder implements DataBuilder {

  private Symbol symbol;
  private String orderID;
  private String clOrdID;
  private String text = "BITMEX_TRADER";

  public DataDeleteOrderBuilder withSymbol(Symbol symbol) {
    this.symbol = symbol;
    return this;
  }

  public DataDeleteOrderBuilder withOrderID(String orderID) {
    this.orderID = orderID;
    return this;
  }

  public DataDeleteOrderBuilder withClientOrderId(String clOrdID) {
    this.clOrdID = clOrdID;
    return this;
  }

  public DataDeleteOrderBuilder withText(String text) {
    this.text = text;
    return this;
  }

  @Override
  public String get() {
    AtomicReference<String> data = new AtomicReference<>();
    Optional.ofNullable(symbol).ifPresent(i -> data.set("symbol=" + i.name()));
    Optional.ofNullable(orderID).ifPresent(i -> data.set(data.get() + "&orderID=" + i));
    Optional.ofNullable(clOrdID).ifPresent(i -> data.set(data.get() + "&clOrdID=" + i));
    Optional.ofNullable(text).ifPresent(i -> data.set(data.get() + "&text=" + i));
    return data.get();
  }
}
