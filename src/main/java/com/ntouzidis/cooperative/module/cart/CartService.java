package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;

import java.util.List;
import java.util.Set;

public interface CartService {

    Cart findByCustomerId(int customerId);

    List<CartProduct> getAllByCustomer(int id);

    List<Cart> getAllSortedAndOrdered(String smb, String omb);

    void addProduct(int cartId, int productId);

    void saveCart(Cart cart);
}
