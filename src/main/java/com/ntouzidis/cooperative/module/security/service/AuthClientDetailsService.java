package com.ntouzidis.cooperative.module.security.service;

import com.ntouzidis.cooperative.module.security.entity.AuthClient;
import com.ntouzidis.cooperative.module.security.entity.AuthClientScope;
import com.ntouzidis.cooperative.module.security.entity.AuthGrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthClientDetailsService implements ClientDetailsService {

    @Autowired
    private AuthClientService authClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        AuthClient authClient = authClientService.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found: " + clientId));

        List<String> grantTypes = authClientService.findGrantTypesByAuthClientId(authClient.getId())
                .stream()
                .map(AuthGrantType::getName)
                .collect(Collectors.toList());

        List<String> scopes = authClientService.findScopesByAuthClientId(authClient.getId())
                .stream()
                .map(AuthClientScope::getName)
                .collect(Collectors.toList());

        List<GrantedAuthority> grantAuthorities = authClientService.findAuthoritiesByAuthClientId(authClient.getId())
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getName()))
                .collect(Collectors.toList());

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(authClient.getSecret());
        clientDetails.setAuthorizedGrantTypes(grantTypes);
        clientDetails.setScope(scopes);
        clientDetails.setAuthorities(grantAuthorities);
        return clientDetails;
    }
}
