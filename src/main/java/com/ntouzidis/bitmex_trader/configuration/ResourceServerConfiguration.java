package com.ntouzidis.bitmex_trader.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
    handler.setUseReferer(true);
    return handler;
  }

  @Bean
  public LogoutSuccessHandler customLogoutHandler() {
    SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
    handler.setUseReferer(true);
    return handler;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().authorizeRequests().antMatchers("/api/v1/**").authenticated();
  }
}
