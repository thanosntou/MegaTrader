package com.ntouzidis.bitmex_trader.module.common.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AdminForm {

  @NonNull
  @JsonProperty("tenantId")
  private Long tenantId;

  @NotBlank
  @JsonProperty("username")
  private String username;

  @NotBlank
  @JsonProperty("email")
  private String email;

  @NotBlank
  @JsonProperty("pass")
  private String pass;

  @NotBlank
  @JsonProperty("confirmPass")
  private String confirmPass;

}
