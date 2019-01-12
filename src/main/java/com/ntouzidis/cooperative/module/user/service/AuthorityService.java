package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.Authority;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.AuthorityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    Set<GrantedAuthority> getAuthorities(String username) {
        return authorityRepository.findAllByUsername(username).stream().map(auth -> new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toSet());
    }

    void createAuthorities(String username, List<GrantedAuthority> authorities) {
        authorities.stream().map(GrantedAuthority::getAuthority).forEach(auth -> authorityRepository.save(new Authority(username, auth)));
    }

    public boolean isCustomer(User user) {
        return Optional.ofNullable(authorityRepository.findByUsernameAndAuthority(user.getUsername(), "ROLE_CUSTOMER")).isPresent();
    }

    public boolean isTrader(User user) {
        return Optional.ofNullable(authorityRepository.findByUsernameAndAuthority(user.getUsername(), "ROLE_TRADER")).isPresent();
    }

}
