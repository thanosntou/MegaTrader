package com.ntouzidis.cooperative.module.user.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.user.entity.Tenant;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.entity.Wallet;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RootService {

  private static final String ROOT = "root";
  private static final String ROOT_EMAIL = "root@email.com";

  private final UserRepository userRepository;
  private final TenantService tenantService;
  private final AuthorityService authorityService;
  private final PasswordEncoder passwordEncoder;

  public RootService(UserRepository userRepository,
                     TenantService tenantService,
                     AuthorityService authorityService,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.tenantService = tenantService;
    this.authorityService = authorityService;
    this.passwordEncoder = passwordEncoder;
  }

  public User setuUp(String pass) {
    Tenant tenant = tenantService.create(ROOT);
    return createRootUser(tenant, pass);
  }

  private User createRootUser(Tenant tenant, String pass) {
    Preconditions.checkArgument(!userRepository.findOneGlobal(ROOT).isPresent(), "username exists");

    User user = new User(ROOT, passwordEncoder.encode(pass));

    Wallet wallet = new Wallet();
    wallet.setBalance(0L);

    user.setTenant(tenant);
    user.setEmail(ROOT_EMAIL);
    user.setWallet(wallet);
    user.setEnabled(false);
    user.setFixedQtyXBTUSD(0);
    user.setFixedQtyETHUSD(0);
    user.setFixedQtyADAZ18(0);
    user.setFixedQtyXBTJPY(0);
    user.setFixedQtyBCHZ18(0);
    user.setFixedQtyEOSZ18(0);
    user.setFixedQtyLTCZ18(0);
    user.setFixedQtyTRXZ18(0);
    user.setFixedQtyXRPZ18(0);
    user.setFixedQtyXBTKRW(0);
    user.setApiKey("Fill_Me");
    user.setApiSecret("Fill_Me");

    userRepository.save(user);
    authorityService.createAuthorities(user.getUsername(), AuthorityUtils.createAuthorityList("ROLE_ROOT"));
    return user;
  }
}
