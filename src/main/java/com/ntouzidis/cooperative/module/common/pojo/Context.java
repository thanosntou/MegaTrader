package com.ntouzidis.cooperative.module.common.pojo;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.Tenant;
import com.ntouzidis.cooperative.module.user.entity.User;

public class Context {

  private String host;
  private String ip;
  private CustomUserDetails customUserDetails;


  public Context() {
  }

  public Tenant getTenant() {
    return customUserDetails.getUser().getTenant();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public CustomUserDetails getCustomUserDetails() {
    return customUserDetails;
  }

  public void setCustomUserDetails(CustomUserDetails customUserDetails) {
    this.customUserDetails = customUserDetails;
  }

  public User getUser() {
    return customUserDetails.getUser();
  }

  public Integer getUserID() {
    return customUserDetails.getUser().getId();
  }
}


