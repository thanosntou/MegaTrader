package com.ntouzidis.cooperative.module.product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findAllByCategory(String category);

    Product getById(int id);

    List<Product> getAllSortedAndOrdered(String sb, String or);

    void saveOrUpdate(Product theProduct);
}
