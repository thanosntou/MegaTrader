package com.ntouzidis.cooperative.module.security.repository;

import com.ntouzidis.cooperative.module.security.entity.AuthClientAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthClientAuthorityRepository extends JpaRepository<AuthClientAuthority, Integer> {

    List<AuthClientAuthority> findByClientId(Integer clientId);
}
