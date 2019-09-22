package com.ntouzidis.cooperative.module.common.pojo;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.Tenant;
import com.ntouzidis.cooperative.module.user.entity.User;

import javax.servlet.http.HttpServletRequest;

public class Context {

  private final HttpServletRequest request;
  private final CustomUserDetails customUserDetails;

  public Context(HttpServletRequest request, CustomUserDetails customUserDetails) {
    this.request = request;
    this.customUserDetails = customUserDetails;
  }

  public CustomUserDetails getCustomUserDetails() {
    return customUserDetails;
  }

  public User getUser() {
    return customUserDetails.getUser();
  }

  public Integer getUserID() {
    return getUser().getId();
  }

  public Tenant getTenant() {
    return customUserDetails.getUser().getTenant();
  }

  public Integer getTenantId() {
    return getTenant().getId();
  }

  public String getAdrress() {
    return request.getRemoteAddr();
  }
}


