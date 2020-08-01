package com.ntouzidis.bitmex_trader.module.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority, Serializable {

  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  // this field, although is redundant,
  // is kept for oauth related action. don't remove
  @Column(name = "username")
  private String username;

  @Getter
  @Setter
  @Column(name = "authority")
  @Enumerated(value = STRING)
  private Role role;

  @Getter
  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Authority() {
  }

  public Authority(Role authority) {
    this.role = authority;
  }

  public void setUser(User user) {
    this.user = user;
    this.username = user.getUsername();
  }

  public String getAuthority() {
    return this.role.name();
  }
}
