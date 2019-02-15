package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AuthorityRepository extends JpaRepository<Authority, String> {

    List<Authority> findAllByUsername(String username);

    Authority findByUsernameAndAuthority(String username, String authority);

    void deleteAllByUsername(String username);

}
