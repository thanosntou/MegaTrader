package com.ntouzidis.cooperative.module.common.pojo;

public class OrderReport {

  private int total;
  private int succeeded;
  private int failed;

  public OrderReport() {
  }

  public Integer getTotal() {
    return total;
  }

  public Integer getSucceeded() {
    return succeeded;
  }

  public Integer getFailed() {
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
}
