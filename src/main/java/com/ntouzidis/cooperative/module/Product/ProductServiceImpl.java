package com.ntouzidis.cooperative.module.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByCategory(String category) {
        return productRepository.findAllByCategory(category);
    }

    @Override
    public List<Product> getAllSortedAndOrdered(String sb, String or) {
            Sort sort = new Sort((or.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), sb);
            return productRepository.findAll(sort);
    }

    @Override
    public Product getById(int id) {
        return productRepository.getOne(id);
    }

}
