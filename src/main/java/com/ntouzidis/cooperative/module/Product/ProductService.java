package com.ntouzidis.cooperative.module.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findAllByCategory(String category);

    Product getById(int id);

    List<Product> getAllSortedAndOrdered(String sb, String or);
}
