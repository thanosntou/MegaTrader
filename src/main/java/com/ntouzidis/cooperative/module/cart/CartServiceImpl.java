package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Cart> getAllSortedAndOrdered(String smb, String omb) {
        Sort sort = new Sort((omb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), smb);
        return cartRepository.findAll(sort);
    }

    @Override
    public Cart saveCart(Cart newCartProduct) {
        return cartRepository.saveAndFlush(newCartProduct);
    }

    @Override
    public Set<Product> getAllByCustomer(int id) {
        return cartRepository.getOne(id).getProducts();
    }
}
