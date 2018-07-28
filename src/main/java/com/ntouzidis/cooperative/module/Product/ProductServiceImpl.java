package com.ntouzidis.cooperative.module.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager em;

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
    public void save(Product p) {
        if(p.getId() != null) {
            em.merge(p);
        } else {
            em.persist(p);
        }

//        Product pro = productRepository.getByName(p.getName());
//        productRepository.update(pro.getId(),p.getName(),p.getCategory(), pro.getDescription(), pro.getQuantity(), p.getPriceBuy(), p.getPriceShop());
    }

    @Override
    public Product getById(int id) {
        return productRepository.getOne(id);
    }

}
