package com.ntouzidis.bitmex_trader.module.common.pojo.bitmex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitmexOrder {

  private String orderID;
  private String clOrdID;
  private String symbol;
  private String side;
  private String orderQty;
  private String ordStatus;
  private String ordType;
  private String price;
  private String stopPx;
  private String transactTime;

  public BitmexOrder() {
  }

  public String getOrderID() {
    return orderID;
  }

  @JsonProperty("orderID")
  public void setOrderID(String orderID) {
    this.orderID = orderID;
  }

  public String getClOrdID() {
    return clOrdID;
  }

  @JsonProperty("clOrdID")
  public void setClOrdID(String clOrdID) {
    this.clOrdID = clOrdID;
  }

  public String getSymbol() {
    return symbol;
  }

  @JsonProperty("symbol")
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSide() {
    return side;
  }

  @JsonProperty("side")
  public void setSide(String side) {
    this.side = side;
  }

  public String getOrdStatus() {
    return ordStatus;
  }

  @JsonProperty("ordStatus")
  public void setOrdStatus(String ordStatus) {
    this.ordStatus = ordStatus;
  }

  public String getOrdType() {
    return ordType;
  }

  @JsonProperty("ordType")
  public void setOrdType(String ordType) {
    this.ordType = ordType;
  }

  public String getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(String price) {
    this.price = price;
  }

  public String getStopPx() {
    return stopPx;
  }

  @JsonProperty("stopPx")
  public void setStopPx(String stopPx) {
    this.stopPx = stopPx;
  }

  public String getTransactTime() {
    return transactTime;
  }

  @JsonProperty("transactTime")
  public void setTransactTime(String transactTime) {
    this.transactTime = transactTime;
  }

  public String getOrderQty() {
    return orderQty;
  }

  @JsonProperty("orderQty")
  public void setOrderQty(String orderQty) {
    this.orderQty = orderQty;
  }
}
