package com.ntouzidis.cooperative.module.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    @Modifying
    @Query("update Offer o set o.active=:status where id=:id")
    int updateOfferStatus(@Param("id")int id, @Param("status")int status);

}
