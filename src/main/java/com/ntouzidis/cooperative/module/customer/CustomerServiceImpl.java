package com.ntouzidis.cooperative.module.customer;

import com.ntouzidis.cooperative.module.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getOne(int id) {
        return customerRepository.getOne(id);
    }

    @Override
    public Customer getOne(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public List<Customer> getSortedAndOrdered(String scb, String ocb) {
        Sort sort = new Sort((ocb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), scb);
        return customerRepository.findAll(sort);
    }

    @Override
    public Customer save(Customer c) {
        return customerRepository.saveAndFlush(c);
    }

    @Override
    public Cart getCart(String username) {
        return null;
    }


}
