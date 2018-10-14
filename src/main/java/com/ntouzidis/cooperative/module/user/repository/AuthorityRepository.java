package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AuthorityRepository extends JpaRepository<Authority, String>, IAuthorityHibernateRepository {

    List<Authority> findAllByUsername(String username);

//    @Override
//    List bringAllByUsername(String username);

    Authority findByUsernameAndAuthority(String username, String authority);

}
