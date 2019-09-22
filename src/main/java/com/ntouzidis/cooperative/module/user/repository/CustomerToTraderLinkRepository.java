package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerToTraderLinkRepository extends JpaRepository<CustomerToTraderLink, Integer> {

    Optional<CustomerToTraderLink> findByCustomer(User customer);

    List<CustomerToTraderLink> findAllByTrader(User trader);

    List<CustomerToTraderLink> findAllByTraderAndGuide(User trader, boolean guide);
}
