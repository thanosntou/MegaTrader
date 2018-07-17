package com.ntouzidis.cooperative.module.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByCategory(String category);


//      They are working
//    List<Product> findAllByOrderByNameAsc();
//
//    List<Product> findAllByOrderByNameDesc();
}
