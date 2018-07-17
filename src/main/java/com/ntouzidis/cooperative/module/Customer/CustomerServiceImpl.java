package com.ntouzidis.cooperative.module.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getSortedAndOrdered(String scb, String ocb) {
        Sort sort = new Sort((ocb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), scb);
        return customerRepository.findAll(sort);
    }

    @Override
    public Customer save(Customer c) {
        return customerRepository.saveAndFlush(c);
    }


}
