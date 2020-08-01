package com.ntouzidis.bitmex_trader.module.user.repository;

import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository  extends JpaRepository<Tenant, Long> {

  Optional<Tenant> findByName(String username);
}
