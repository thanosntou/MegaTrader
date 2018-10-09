package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerToTraderLinkRepository extends JpaRepository<CustomerToTraderLink, Integer> {

    CustomerToTraderLink findByCustomer(User user);

//    @Modifying
//    @Query("update Offer o set o.active=:status where id=:id")
//    int updateOfferStatus(@Param("id")int id, @Param("status")int status);
}
