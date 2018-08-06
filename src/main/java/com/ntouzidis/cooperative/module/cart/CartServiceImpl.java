package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;
import com.ntouzidis.cooperative.module.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Cart> getAllSortedAndOrdered(String smb, String omb) {
        Sort sort = new Sort((omb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), smb);
        return cartRepository.findAll(sort);
    }

    @Override
    @Transactional
    public void addProduct(int cartId, int productId) {
        Cart cart = cartRepository.getOne(cartId);
        Product product = productRepository.getOne(productId);
        cart.addProduct(product);

        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart findByCustomerId(int customerId) {
        return cartRepository.getOne(customerId);
    }

    @Override
    public List<CartProduct> getAllByCustomer(int id) {
        return cartRepository.getOne(id).getProducts();
    }
}
