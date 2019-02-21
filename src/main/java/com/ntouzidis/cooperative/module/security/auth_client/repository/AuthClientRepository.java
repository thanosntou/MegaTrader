package com.ntouzidis.cooperative.module.security.auth_client.repository;

import com.ntouzidis.cooperative.module.security.auth_client.entity.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthClientRepository extends JpaRepository<AuthClient, Integer> {

    Optional<AuthClient> findByClientId(String clientId);
}
