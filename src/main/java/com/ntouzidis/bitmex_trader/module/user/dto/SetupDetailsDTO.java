package com.ntouzidis.bitmex_trader.module.user.dto;

import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.provider.ClientDetails;

@Getter
@Setter
public class SetupDetailsDTO {

  private ClientDetails clientDetails;

  private Tenant tenant;

  private User user;
}
