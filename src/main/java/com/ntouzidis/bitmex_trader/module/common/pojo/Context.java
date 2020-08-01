package com.ntouzidis.bitmex_trader.module.common.pojo;

import com.ntouzidis.bitmex_trader.module.user.entity.Authority;
import com.ntouzidis.bitmex_trader.module.user.entity.CustomUserDetails;
import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Context {

  private final HttpServletRequest request;
  private final CustomUserDetails customUserDetails;

  public Context(HttpServletRequest request, CustomUserDetails customUserDetails) {
    this.request = request;
    this.customUserDetails = customUserDetails;
  }

  public CustomUserDetails getUserDetails() {
    return customUserDetails;
  }

  public User getUser() {
    return customUserDetails.getUser();
  }

  public List<Authority> getAuthorities() {
    return customUserDetails.getAuthorities();
  }

  public Long getUserID() {
    return getUser().getId();
  }

  public Tenant getTenant() {
    return customUserDetails.getUser().getTenant();
  }

  public Long getTenantId() {
    return getTenant().getId();
  }

  public String getAddress() {
    return request.getRemoteAddr();
  }
}


