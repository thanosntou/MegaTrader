package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> findByUsername(String username);

    User createCustomer(User user, String password);

    User createTrader(User user, String password);

}
