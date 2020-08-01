package com.ntouzidis.bitmex_trader.module.auth_client.service;

import com.google.common.collect.ImmutableSet;
import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthClientScope;
import com.ntouzidis.bitmex_trader.module.auth_client.repository.AuthClientAuthorityRepository;
import com.ntouzidis.bitmex_trader.module.auth_client.repository.AuthClientScopeRepository;
import com.ntouzidis.bitmex_trader.module.auth_client.repository.AuthGrantTypeRepository;
import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthClient;
import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthClientAuthority;
import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthGrantType;
import com.ntouzidis.bitmex_trader.module.auth_client.repository.AuthClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthClientService {

  public static final String TEST = "test";
  private static final String PASSWORD = "password";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final String READ = "read";
  private static final String WRITE = "write";
  private static final String TRUST = "trust";
  private static final String ROLE_CLIENT = "ROLE_CLIENT";
  private static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";
  public static final String IMPLICIT = "implicit";
  public static final String AUTHORIZATION_CODE = "authorization_code";
  private final AuthClientRepository authClientRepository;
  private final AuthGrantTypeRepository authGrantTypeRepository;
  private final AuthClientScopeRepository authClientScopeRepository;
  private final AuthClientAuthorityRepository authClientAuthorityRepository;

  Optional<AuthClient> findByClientId(String clientId) {
    return authClientRepository.findByClientId(clientId);
  }

  List<AuthClientScope> findScopesByAuthClientId(Long authClientId) {
    return authClientScopeRepository.findByClientId(authClientId);
  }

  List<AuthGrantType> findGrantTypesByAuthClientId(Long authClientId) {
    return authGrantTypeRepository.findByClientId(authClientId);
  }

  List<AuthClientAuthority> findAuthoritiesByAuthClientId(Long authClientId) {
    return authClientAuthorityRepository.findByClientId(authClientId);
  }

  public ClientDetails createInitialClient() {
    AuthClient client = authClientRepository.save(new AuthClient(TEST, "$2a$10$ZrbYZbtDkHcNBn1HtnvjCectGjOW1QEvMYr9XWK59bzt5PxDx6pq."));

    List<GrantedAuthority> grantAuthorities = authClientAuthorityRepository.saveAll(ImmutableSet.of(
        new AuthClientAuthority(client.getId(), ROLE_CLIENT),
        new AuthClientAuthority(client.getId(), ROLE_TRUSTED_CLIENT)))
        .stream()
        .map(auth -> new SimpleGrantedAuthority(auth.getName()))
        .collect(Collectors.toList());

    List<String> grantTypes = authGrantTypeRepository.saveAll(ImmutableSet.of(
        new AuthGrantType(client.getId(), PASSWORD),
        new AuthGrantType(client.getId(), AUTHORIZATION_CODE),
        new AuthGrantType(client.getId(), IMPLICIT),
        new AuthGrantType(client.getId(), REFRESH_TOKEN)))
        .stream()
        .map(AuthGrantType::getName)
        .collect(Collectors.toList());

    List<String> scopes = authClientScopeRepository.saveAll(ImmutableSet.of(
        new AuthClientScope(client.getId(), READ),
        new AuthClientScope(client.getId(), WRITE),
        new AuthClientScope(client.getId(), TRUST)))
        .stream()
        .map(AuthClientScope::getName)
        .collect(Collectors.toList());

    BaseClientDetails clientDetails = new BaseClientDetails();
    clientDetails.setClientId(client.getClientId());
    clientDetails.setClientSecret(client.getSecret());
    clientDetails.setAuthorizedGrantTypes(grantTypes);
    clientDetails.setScope(scopes);
    clientDetails.setAuthorities(grantAuthorities);

    return clientDetails;
  }
}
