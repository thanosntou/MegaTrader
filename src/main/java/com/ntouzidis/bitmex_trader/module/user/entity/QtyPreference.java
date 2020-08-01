package com.ntouzidis.bitmex_trader.module.user.entity;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "qty_preference")
public class QtyPreference implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private Symbol symbol;

  private Integer value;

  public QtyPreference() {
  }

  public QtyPreference(User user, Symbol symbol, Integer value) {
    this.user = user;
    this.symbol = symbol;
    this.value = value;
  }


}
