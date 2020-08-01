package com.ntouzidis.bitmex_trader.module.user.repository;

import com.ntouzidis.bitmex_trader.module.user.entity.FollowerToTraderLink;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerToTraderLinkRepository extends JpaRepository<FollowerToTraderLink, Long> {

  Optional<FollowerToTraderLink> findByFollower(User customer);

  List<FollowerToTraderLink> findAllByTrader(User trader);

  List<FollowerToTraderLink> findAllByTraderAndGuide(User trader, boolean guide);
}
