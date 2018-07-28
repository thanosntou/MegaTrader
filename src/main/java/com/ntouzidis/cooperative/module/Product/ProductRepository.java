package com.ntouzidis.cooperative.module.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product getByName(String name);

    List<Product> findAllByCategory(String category);

    @Modifying
    @Query("update Product p set p.name=:n, p.category=:c, p.description=:d, p.quantity=:q, p.priceBuy=:pb, p.priceShop=:ps where p.id=:id")
    void update(@Param("id")int id,
                @Param("n")String name,
                @Param("c")String category,
                @Param("d")String description,
                @Param("q")int quantity,
                @Param("pb")double price_buy,
                @Param("ps")double price_shop);

}
