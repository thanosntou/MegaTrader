package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;

import java.util.List;

public interface RootService {

    User getTraderGlobally(Long traderId);

    List<User> getFollowersByTrader(Long traderId);

    /**
     * Delete a user of role Follower, providing it's ID or the entity
     *
     * @param follower entity of Follower user to delete
     * @param id of the Follower user to delete, will be used if follower param is null
     * @return the deleted Follower user
     */
    User deleteFollowerUser(User follower, Long id);

    /**
     * Delete a user of role Trader, providing it's ID or the entity
     *
     * @param trader entity of Trader user to delete
     * @param id of the Trader user to delete, will be used if trader param is null
     * @return the deleted Trader user
     */
    User deleteTraderUser(User trader, Long id);

    /**
     * Delete a user of role Admin, providing it's ID or the entity
     *
     * @param admin entity of Admin user to delete
     * @param id of the Admin user to delete, will be used if admin param is null
     * @return the deleted Admin user
     */
    User deleteAdminUser(User admin, Long id);

    Tenant deleteTenant(Long id);
}
