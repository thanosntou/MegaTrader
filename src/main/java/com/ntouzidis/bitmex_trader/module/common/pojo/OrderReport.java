package com.ntouzidis.bitmex_trader.module.common.pojo;

import java.io.Serializable;

public class OrderReport implements Serializable {

  private int total;
  private int succeeded;
  private int failed;

  public OrderReport() {
  }

  public int getTotal() {
    return total;
  }

  public int getSucceeded() {
    return succeeded;
  }

  public int getFailed() {
    return failed;
  }

  public void addOneSucceeded() {
    this.succeeded += 1;
    this.total += 1;
  }

  public void addOneFailed() {
    this.failed += 1;
    this.total += 1;
  }

  @Override
  public String toString() {
    return "OrderReport{" +
        "total=" + total +
        ", succeeded=" + succeeded +
        ", failed=" + failed +
        '}';
  }
}
