package com.ntouzidis.cooperative.module.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getById(int id) {
        return productRepository.getOne(id);
    }

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
    @Transactional
    public void saveOrUpdate(Product p) {
        productRepository.saveAndFlush(p);
    }

    @Override
    public void delete(int theId) {
        productRepository.deleteById(theId);
    }
}
