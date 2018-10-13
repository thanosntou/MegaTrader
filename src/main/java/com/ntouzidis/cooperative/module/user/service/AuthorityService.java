package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.Authority;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.AuthorityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    Authority findAuthority(String username) {
        return authorityRepository.findById(username).orElseThrow(RuntimeException::new);
    }

    void createAuthorities(String username, List<GrantedAuthority> authorities) {
        authorities.stream().map(GrantedAuthority::getAuthority).forEach(auth -> authorityRepository.save(new Authority(username, auth)));
    }

    boolean isCustomer(User user) {
        return Optional.ofNullable(authorityRepository.findByUsernameAndAuthority(user.getUsername(), "ROLE_CUSTOMER")).isPresent();
    }

    boolean isTrader(User user) {
        return Optional.ofNullable(authorityRepository.findByUsernameAndAuthority(user.getUsername(), "ROLE_TRADER")).isPresent();
    }

}
