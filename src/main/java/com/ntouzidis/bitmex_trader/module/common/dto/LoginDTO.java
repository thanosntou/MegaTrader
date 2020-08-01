package com.ntouzidis.bitmex_trader.module.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(NON_NULL)
public class LoginDTO {

  @JsonProperty
  private Long id;

  @JsonProperty
  private User user;

  @JsonProperty
  private LocalDateTime createdOn;
}
