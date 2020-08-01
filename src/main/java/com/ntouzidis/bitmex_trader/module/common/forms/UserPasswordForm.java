package com.ntouzidis.bitmex_trader.module.common.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserPasswordForm {

  @NotBlank
  @JsonProperty("newPass")
  private String newPass;

  @NotBlank
  @JsonProperty("confirmPass")
  private String confirmPass;

}
