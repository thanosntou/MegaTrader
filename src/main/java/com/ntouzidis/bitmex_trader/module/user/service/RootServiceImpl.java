package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RootServiceImpl implements RootService {

    private final UserService userService;
    private final TenantService tenantService;

    @Override
    public User deleteFollowerUser(User follower, Long id) {
        User followerUser = Optional.ofNullable(follower).orElseGet(() -> userService.getFollower(id));
        if (userService.getPersonalTrader(followerUser).isPresent())
            userService.unlinkTrader(followerUser);
        return userService.delete(followerUser);
    }

    @Override
    public User deleteTraderUser(User trader, Long id) {
        User traderUser = Optional.ofNullable(trader).orElseGet(() -> userService.getTrader(id));
        userService.getFollowersByTrader(traderUser.getId()).forEach(userService::unlinkTrader);
        return userService.delete(traderUser);
    }

    @Override
    public User deleteAdminUser(User admin, Long id) {
        User adminUser = Optional.ofNullable(admin).orElseGet(() -> userService.getAdmin(id));
        return userService.delete(adminUser);
    }

    @Override
    public Tenant deleteTenant(Long tenantId) {
        userService.getFollowersByTenant(tenantId).forEach(follower -> deleteFollowerUser(follower, null));
        userService.getTradersByTenant(tenantId).forEach(trader -> deleteTraderUser(trader, null));
        userService.getAdminsByTenant(tenantId).forEach(admin -> deleteAdminUser(admin, null));
        return tenantService.delete(tenantId);
    }
}
