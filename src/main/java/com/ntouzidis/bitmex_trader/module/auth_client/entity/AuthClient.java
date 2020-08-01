package com.ntouzidis.bitmex_trader.module.auth_client.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "authClient")
public class AuthClient implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "client_id")
  private String clientId;

  @Column(name = "secret")
  private String secret;

  public AuthClient(String clientId, String secret) {
    this.clientId = clientId;
    this.secret = secret;
  }


}
