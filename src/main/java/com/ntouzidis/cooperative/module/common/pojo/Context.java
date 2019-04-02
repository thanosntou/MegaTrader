package com.ntouzidis.cooperative.module.common.pojo;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;

public class Context {

  private String tenantID;
  private String host;
  private String ip;
  private CustomUserDetails customUserDetails;


  public Context() {
  }

  public String getTenantID() {
    return tenantID;
  }

  public void setTenantID(String tenantID) {
    this.tenantID = tenantID;
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


