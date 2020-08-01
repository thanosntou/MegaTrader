package com.ntouzidis.bitmex_trader.module.common.enumeration;

// Used enumeration for the roles to be able to stream them
public enum Role {

  ROOT(Constants.ROOT),
  ADMIN(Constants.ADMIN),
  TRADER(Constants.TRADER),
  FOLLOWER(Constants.FOLLOWER);

  private final String value;

  Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  // This class helps so i can define another static fields using these roles
  // like AuthorizationConstants class
  public static class Constants {
    public static final String ROOT = "ROOT";
    public static final String ADMIN = "ADMIN";
    public static final String TRADER = "TRADER";
    public static final String FOLLOWER = "FOLLOWER";
  }

}
