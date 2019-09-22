package com.ntouzidis.cooperative.module.common;

public class RolesConstants {

  public static final String ADMIN_ROLE = "hasRole('ADMIN')";

  public static final String CUSTOMER_ROLE = "hasRole('CUSTOMER')";

  public static final String TRADER_ROLE = "hasRole('TRADER')";

  public static final String ROOT_ROLE = "hasRole('ROOT')";

  public static final String AUTHENTICATED = "isAuthenticated()";

  private RolesConstants() {
  }
}
