package com.ntouzidis.bitmex_trader.module.common.event;

import com.ntouzidis.bitmex_trader.module.user.entity.CustomUserDetails;
import com.ntouzidis.bitmex_trader.module.user.entity.Login;
import com.ntouzidis.bitmex_trader.module.user.repository.LoginRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

  private final LoginRepository loginRepository;

  public AuthenticationSuccessEventListener(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @Override
  public void onApplicationEvent(final AuthenticationSuccessEvent event) {
    Authentication authentication = event.getAuthentication();
    if (authentication instanceof UsernamePasswordAuthenticationToken
        && authentication.getPrincipal() != null
        && authentication.getPrincipal() instanceof CustomUserDetails
    ) {
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

      Login login = new Login();
      login.setTenant(userDetails.getUser().getTenant());
      login.setUser(userDetails.getUser());

      loginRepository.save(login);
    }

  }
}
