package com.ntouzidis.bitmex_trader.configuration;

import com.ntouzidis.bitmex_trader.module.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;

  @Qualifier("userDetailsService")
  private final CustomUserDetailsService userDetailsService;

  public WebSecurityConfiguration(DataSource dataSource,
                                  CustomUserDetailsService userDetailsService) {
    this.dataSource = dataSource;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .authenticationProvider(authProvider())
            .jdbcAuthentication()
            .dataSource(dataSource);
  }

  @Override
  @Bean(name="authenticationManager")
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  /**
   * This endpoint doesn't require authentication. For the sign-up.
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(POST,"/api/v1/user");
  }
}
