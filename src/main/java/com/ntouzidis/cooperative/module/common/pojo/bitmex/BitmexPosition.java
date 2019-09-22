package com.ntouzidis.cooperative.module.common.pojo.bitmex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitmexPosition {

  private Symbol symbol;
  private Long currentQty;
  private Double homeNotional;
  private Double avgEntryPrice;
  private Double markPrice;
  private Double liquidationPrice;
  private Long maintMargin;
  private Integer leverage;
  private Boolean isOpen;

  public BitmexPosition() {
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public Long getSize() {
    return currentQty;
  }

  public Double getHomeNotional() {
    return homeNotional;
  }

  public Double getEntryPrice() {
    return avgEntryPrice;
  }

  public Double getMarkPrice() {
    return markPrice;
  }

  public Double getLiquidationPrice() {
    return liquidationPrice;
  }

  public Long getMaintMargin() {
    return maintMargin;
  }

  public Integer getLeverage() {
    return leverage;
  }

  public Boolean isOpen() {
    return isOpen;
  }

  @JsonProperty("symbol")
  public void setSymbol(Symbol symbol) {
    this.symbol = symbol;
  }

  @JsonProperty("currentQty")
  public void setSize(Long currentQty) {
    this.currentQty = currentQty;
  }

  @JsonProperty("homeNotional")
  public void setHomeNotional(Double homeNotional) {
    this.homeNotional = homeNotional;
  }

  @JsonProperty("avgEntryPrice")
  public void setEntryPrice(Double avgEntryPrice) {
    this.avgEntryPrice = avgEntryPrice;
  }

  @JsonProperty("markPrice")
  public void setMarkPrice(Double markPrice) {
    this.markPrice = markPrice;
  }

  @JsonProperty("liquidationPrice")
  public void setLiquidationPrice(Double liquidationPrice) {
    this.liquidationPrice = liquidationPrice;
  }

  @JsonProperty("maintMargin")
  public void setMaintMargin(Long maintMargin) {
    this.maintMargin = maintMargin;
  }

  @JsonProperty("leverage")
  public void setLeverage(Integer leverage) {
    this.leverage = leverage;
  }

  @JsonProperty("isOpen")
  public void setisOpen(Boolean open) {
    isOpen = open;
  }
}
