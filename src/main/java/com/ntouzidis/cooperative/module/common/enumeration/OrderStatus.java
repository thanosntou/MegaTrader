package com.ntouzidis.cooperative.module.common.enumeration;

public enum OrderStatus {

  New("New"),
  Filled("Filled"),
  Cancelled("Cancelled");

  private final String value;

  OrderStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
