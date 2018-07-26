package com.ntouzidis.cooperative.module.Cart;

import com.ntouzidis.cooperative.module.Customer.Customer;

import java.util.List;

public interface CartService {

    List<Cart> getAllByCustomer(Customer c);

    List<Cart> getAllSortedAndOrdered(String smb, String omb);

    Cart saveCart(Cart newCartProduct);
}
