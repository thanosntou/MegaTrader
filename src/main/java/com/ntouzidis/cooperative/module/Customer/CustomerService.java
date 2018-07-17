package com.ntouzidis.cooperative.module.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getSortedAndOrdered(String scb, String ocb);

    Customer save(Customer c);

}
