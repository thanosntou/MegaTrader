package com.ntouzidis.bitmex_trader.module.common.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Client;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateForm {

  @JsonProperty("apiKey")
  private String apiKey;

  @JsonProperty("apiSecret")
  private String apiSecret;

  @JsonProperty("client")
  private Client client;

  @JsonProperty("symbol")
  private Symbol symbol;

  @JsonProperty("qty")
  private Integer qty;

}
