package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

  List<User> findAllByTenantId(Integer tenantId);

  Optional<User> findByTenantIdAndId(Integer tenantId, Integer username);

  Optional<User> findByTenantIdAndUsername(Integer tenantId, String username);

  Optional<User> findByUsername(String username);

}
