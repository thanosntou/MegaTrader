package com.ntouzidis.bitmex_trader.module.common.constants;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Role;

public class AuthorizationConstants {

  private static final String HAS_AUTHORITY = "hasAuthority('";

  public static final String ADMIN = HAS_AUTHORITY + Role.Constants.ADMIN +"')";

  public static final String FOLLOWER = HAS_AUTHORITY + Role.Constants.FOLLOWER + "')";

  public static final String TRADER = HAS_AUTHORITY + Role.Constants.TRADER + "')";

  public static final String ROOT = HAS_AUTHORITY + Role.Constants.ROOT + "')";


  private AuthorizationConstants() {
  }
}
