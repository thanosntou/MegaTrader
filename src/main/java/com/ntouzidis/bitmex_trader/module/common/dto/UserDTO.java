package com.ntouzidis.bitmex_trader.module.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Client;
import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(NON_NULL)
public class UserDTO implements Serializable {

  @JsonProperty
  private Long id;

  @JsonProperty()
  private Tenant tenant;

  @JsonProperty
  private String username;

  @JsonProperty
  private String password;

  @JsonProperty
  private Set<String> authorities;

  @JsonProperty
  private String email;

  @JsonProperty
  private Boolean enabled;

  @JsonProperty
  private String apiKey;

  @JsonProperty
  private String apiSecret;

  @JsonProperty
  private Client client;

  @JsonProperty
  private LocalDateTime createdOn;
}
