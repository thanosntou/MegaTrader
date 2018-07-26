package com.ntouzidis.cooperative.module.Cart;

import com.ntouzidis.cooperative.module.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByCustomer(Customer c);
}
