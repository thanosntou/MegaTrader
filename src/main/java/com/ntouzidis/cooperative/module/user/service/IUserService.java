package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    Optional<User> findByUsername(String username);

    List<User> getTraders();

    User getPersonalTrader(String username);

    User createCustomer(User user, String password);

    User createTrader(User user, String password);

    void linkTrader(User user, int traderId);

    void unlinkTrader(User user, int traderId);

}
