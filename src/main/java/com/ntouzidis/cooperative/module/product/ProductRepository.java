package com.ntouzidis.cooperative.module.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer>, ProductDAO {

    List<Product> findAllByCategory(String category);



}
