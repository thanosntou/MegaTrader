package com.ntouzidis.cooperative.module.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface IAuthorityService {

    void createAuthorities(String username, List<GrantedAuthority> authorities);

}
