package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.Authority;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class AuthorityHibernateRepository implements IAuthorityHibernateRepository {

    private SessionFactory sessionFactory;

//    @Override
//    public List bringAllByUsername(String username) {
//        Session session = sessionFactory.openSession();
////        session.beginTransaction();
//        Query query = session.createQuery("SELECT a FROM Authority a where username = :uname ")
//                .setParameter("uname", username);
//
//        return query.getResultList();
//    }


}
