package com.ntouzidis.bitmex_trader.module.auth_client.repository;

import com.ntouzidis.bitmex_trader.module.auth_client.entity.AuthGrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AuthGrantTypeRepository extends JpaRepository<AuthGrantType, Long> {

  List<AuthGrantType> findByClientId(Long authClientId);
}
