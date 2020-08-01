package com.ntouzidis.bitmex_trader.module.user.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "follower_to_trader")
public class FollowerToTraderLink {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "follower_id")
  private User follower;

  @ManyToOne
  @JoinColumn(name = "trader_id")
  private User trader;

  @Column(name = "guide")
  private Boolean guide;

  @Column(name = "is_hidden")
  private Boolean isHidden;

  @Column(name = "created_on")
  private LocalDateTime createdOn = now();

  public FollowerToTraderLink() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getFollower() {
    return follower;
  }

  public void setFollower(User follower) {
    this.follower = follower;
  }

  public User getTrader() {
    return trader;
  }

  public void setTrader(User trader) {
    this.trader = trader;
  }

  public Boolean getHidden() {
    return isHidden;
  }

  public void setHidden(Boolean hidden) {
    isHidden = hidden;
  }

  public Boolean getGuide() {
    return guide;
  }

  public void setGuide(Boolean guide) {
    this.guide = guide;
  }

  public Boolean getIsHidden() {
    return isHidden;
  }

  public void setIsHidden(Boolean hidden) {
    isHidden = hidden;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }
}
