package com.ntouzidis.bitmex_trader.module.user.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "login")
public class Login implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "created_on")
  private LocalDateTime createdOn = now();

  public Login() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }
}
