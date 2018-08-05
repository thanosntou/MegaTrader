package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;

import java.util.List;

public interface CartService {

    List<Product> getAllByCustomer(Customer c);

    List<Cart> getAllSortedAndOrdered(String smb, String omb);

    Cart saveCart(Cart newCartProduct);
}
