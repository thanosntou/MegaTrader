package com.ntouzidis.cooperative.module.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService implements IAuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }


    public void createAuthorities(String username, List<GrantedAuthority> authorities) {
        for (GrantedAuthority x: authorities){
            authorityRepository.save(new Authority(username, x.getAuthority()));
        }
    }

}
