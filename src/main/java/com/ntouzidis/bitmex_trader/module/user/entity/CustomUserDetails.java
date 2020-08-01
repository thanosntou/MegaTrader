package com.ntouzidis.bitmex_trader.module.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CustomUserDetails implements UserDetails {

  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @JsonIgnore
  public String getUsername() {
    return user.getUsername();
  }

  @JsonIgnore
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public List<Authority> getAuthorities() {
    return user.getAuthorities();
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

}
