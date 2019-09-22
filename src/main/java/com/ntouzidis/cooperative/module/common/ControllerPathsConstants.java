package com.ntouzidis.cooperative.module.common;

import static com.ntouzidis.cooperative.module.common.ParamsConstants.ID_PARAM;

public final class ControllerPathsConstants {

  public static final String ID_PATH = "/{" + ID_PARAM + "}";

  public static final String ADMIN_CONTROLLER_PATH = "/api/v1/admin";

  public static final String CUSTOMER_CONTROLLER_PATH = "/api/v1/customer";

  public static final String DASHBOARD_CONTROLLER_PATH = "/api/v1/dashboard";

  public static final String FOLLOWER_CONTROLLER_PATH = "/api/v1/follower";

  public static final String ROOT_CONTROLLER_PATH = "/api/v1/root";

  public static final String TENANT_CONTROLLER_PATH = "/api/v1/tenant";

  public static final String TRADE_CONTROLLER_PATH = "/api/v1/trade";

  public static final String TRADER_CONTROLLER_PATH = "/api/v1/trader";

  public static final String USER_CONTROLLER_PATH = "/api/v1/user";

  private ControllerPathsConstants() {
  }
}
