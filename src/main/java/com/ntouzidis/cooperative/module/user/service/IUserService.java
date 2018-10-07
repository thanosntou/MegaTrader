package com.ntouzidis.cooperative.module.user;

public interface IUserService {

    User createCustomer(String username, String password);

    User createTrader(String username, String password);

}
