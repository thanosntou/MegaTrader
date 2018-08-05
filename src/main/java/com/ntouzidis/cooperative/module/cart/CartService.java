package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;

import java.util.List;
import java.util.Set;

public interface CartService {

    Set<Product> getAllByCustomer(int id);

    List<Cart> getAllSortedAndOrdered(String smb, String omb);

    Cart saveCart(Cart newCartProduct);
}
