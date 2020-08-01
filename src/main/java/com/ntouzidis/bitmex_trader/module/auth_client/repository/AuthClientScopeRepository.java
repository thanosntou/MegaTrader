package com.ntouzidis.bitmex_trader.module.auth_client.repository;

import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthClientScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AuthClientScopeRepository extends JpaRepository<AuthClientScope, Long> {

  List<AuthClientScope> findByClientId(Long clientId);
}
