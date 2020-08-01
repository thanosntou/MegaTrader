package com.ntouzidis.bitmex_trader.module.user.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tenant")
public class Tenant implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "created_on")
  private LocalDateTime createdOn = now();

  public Tenant() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }
}
