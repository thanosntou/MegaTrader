package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import com.ntouzidis.bitmex_trader.module.user.entity.CustomUserDetails;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String username) {
    User user = userRepository.findOneGlobal(username).orElseThrow(NotFoundException::new);
    CustomUserDetails customUserDetail = new CustomUserDetails();
    customUserDetail.setUser(user);
    return customUserDetail;
  }
}
