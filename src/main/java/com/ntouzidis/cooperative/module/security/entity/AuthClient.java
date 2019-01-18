package com.ntouzidis.cooperative.module.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "authClient")
public class AuthClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "secret")
    private String secret;
}
