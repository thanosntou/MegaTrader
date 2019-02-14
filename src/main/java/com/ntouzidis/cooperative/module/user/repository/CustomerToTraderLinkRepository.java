package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerToTraderLinkRepository extends JpaRepository<CustomerToTraderLink, Integer> {

    CustomerToTraderLink findByCustomer(User customer);

    List<CustomerToTraderLink> findAllByTrader(User trader);

    List<CustomerToTraderLink> findAllByTraderAndGuide(User trader, boolean guide);

//    @Modifying
//    @Query("update Offer o set o.active=:status where id=:id")
//    int updateOfferStatus(@Param("id")int id, @Param("status")int status);
}
