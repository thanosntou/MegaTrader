package com.ntouzidis.cooperative.module.customer;

import com.ntouzidis.cooperative.module.cart.Cart;

import java.util.List;

public interface CustomerService {

    Customer getOne(int id);

    Customer getOne(String username);

    List<Customer> getSortedAndOrdered(String scb, String ocb);

    Customer save(Customer c);

    Cart getCart(String username);

}
