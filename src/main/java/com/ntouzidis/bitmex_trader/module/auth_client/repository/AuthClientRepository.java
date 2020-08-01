package com.ntouzidis.bitmex_trader.module.auth_client.repository;

import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AuthClientRepository extends JpaRepository<AuthClient, Long> {

  Optional<AuthClient> findByClientId(String clientId);
}
