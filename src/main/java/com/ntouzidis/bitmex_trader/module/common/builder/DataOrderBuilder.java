package com.ntouzidis.bitmex_trader.module.common.builder;

import com.ntouzidis.bitmex_trader.module.common.enumeration.OrderType;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Side;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DataOrderBuilder implements DataBuilder {

  private Symbol symbol;
  private String clOrdID;
  private Side side;
  private OrderType orderType;
  private String orderQty;
  private Integer displayQty;
  private String price;
  private String execInst;
  private String stopPrice;
  private String text = "BITMEXCALLBOT";

  public DataOrderBuilder() {
  }

  public DataOrderBuilder withClOrdId(String clOrdID) {
    this.clOrdID = clOrdID;
    return this;
  }

  public DataOrderBuilder withSymbol(Symbol symbol) {
    this.symbol = symbol;
    return this;
  }

  public DataOrderBuilder withSide(Side side) {
    this.side = side;
    return this;
  }

  public DataOrderBuilder withOrderType(OrderType orderType) {
    this.orderType = orderType;
    return this;
  }

  public DataOrderBuilder withOrderQty(String orderQty) {
    this.orderQty = orderQty;
    return this;
  }

  public DataOrderBuilder withPrice(String price) {
    this.price = price;
    return this;
  }

  public DataOrderBuilder withExecInst(String execInst) {
    this.execInst = execInst;
    return this;
  }

  public DataOrderBuilder withStopPrice(String stopPrice) {
    this.stopPrice = stopPrice;
    return this;
  }

  public DataOrderBuilder withText(String text) {
    this.text = text;
    return this;
  }

  public DataOrderBuilder withDisplayQty(Integer displayQty) {
    this.displayQty = displayQty;
    return this;
  }

  @Override
  public String get() {
    AtomicReference<String> data = new AtomicReference<>();
    data.set("");
    Optional.of(symbol).ifPresent(i -> data.set(data.get() + "symbol=" + i.name()));
    Optional.ofNullable(clOrdID).ifPresent(i -> data.set(data.get() + "&clOrdID=" + i));
    Optional.ofNullable(side).ifPresent(i -> data.set(data.get() + "&side=" + i.getValue()));
    Optional.ofNullable(orderType).ifPresent(i -> data.set(data.get() + "&ordType=" + i.getValue()));
    Optional.ofNullable(orderQty).ifPresent(i -> data.set(data.get() + "&orderQty=" + i));
    Optional.ofNullable(displayQty).ifPresent(i -> data.set(data.get() + "&displayQty=" + i));
    Optional.ofNullable(price).ifPresent(i -> data.set(data.get() + "&price=" + i));
    Optional.ofNullable(execInst).ifPresent(i -> data.set(data.get() + "&execInst=" + i));
    Optional.ofNullable(stopPrice).ifPresent(i -> data.set(data.get() + "&stopPrice=" + i));
    Optional.ofNullable(text).ifPresent(i -> data.set(data.get() + "&text=" + i));
    return data.get();
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public String getClOrdID() {
    return clOrdID;
  }

  public Side getSide() {
    return side;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public String getOrderQty() {
    return orderQty;
  }

  public String getPrice() {
    return price;
  }

  public String getExecInst() {
    return execInst;
  }

  public String getStopPrice() {
    return stopPrice;
  }

  public String getText() {
    return text;
  }
}
