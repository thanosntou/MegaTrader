package com.ntouzidis.cooperative.module.security.auth_client.repository;

import com.ntouzidis.cooperative.module.security.auth_client.entity.AuthClientScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthClientScopeRepository extends JpaRepository<AuthClientScope, Integer> {

    List<AuthClientScope> findByClientId(Integer clientId);
}
