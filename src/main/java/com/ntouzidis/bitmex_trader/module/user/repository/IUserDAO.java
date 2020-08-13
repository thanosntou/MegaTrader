package com.ntouzidis.bitmex_trader.module.user.repository;

import com.ntouzidis.bitmex_trader.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserDAO extends JpaRepository<User, Long> {

  List<User> findAllByTenantId(Long tenantId);

  Optional<User> findByTenantIdAndId(Long tenantId, Long id);

  Optional<User> findByTenantIdAndUsername(Long tenantId, String username);

  Optional<User> findById(Long id);

  Optional<User> findByUsername(String username);
}
