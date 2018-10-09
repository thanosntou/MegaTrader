package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.Authority;
import com.ntouzidis.cooperative.module.user.repository.AuthorityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority findAuthorities(String username) {
        return authorityRepository.findById(username).orElseThrow(RuntimeException::new);
    }


    public void createAuthorities(String username, List<GrantedAuthority> authorities) {
        for (GrantedAuthority x: authorities){
            authorityRepository.save(new Authority(username, x.getAuthority()));
        }
    }

}
