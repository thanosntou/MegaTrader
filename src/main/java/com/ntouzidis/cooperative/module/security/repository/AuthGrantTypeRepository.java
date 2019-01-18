package com.ntouzidis.cooperative.module.security.repository;

import com.ntouzidis.cooperative.module.security.entity.AuthGrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGrantTypeRepository extends JpaRepository<AuthGrantType, Integer> {

    List<AuthGrantType> findByClientId(Integer authClientId);
}
