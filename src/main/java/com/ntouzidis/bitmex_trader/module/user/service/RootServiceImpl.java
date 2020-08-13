package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import com.ntouzidis.bitmex_trader.module.common.utils.UserUtils;
import com.ntouzidis.bitmex_trader.module.user.entity.FollowerToTraderLink;
import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.repository.FollowerToTraderLinkRepository;
import com.ntouzidis.bitmex_trader.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ntouzidis.bitmex_trader.module.common.constants.MessagesConstants.TRADER_NOT_FOUND_BY_ID;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RootServiceImpl implements RootService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TenantService tenantService;
    private final FollowerToTraderLinkRepository followerToTraderLinkRepository;

    @Override
    public User getTraderGlobally(Long traderId) {
        return userRepository.findOneGlobal(traderId)
                .filter(UserUtils::isTrader)
                .orElseThrow(() -> new NotFoundException(format(TRADER_NOT_FOUND_BY_ID, traderId)));
    }

    @Override
    public List<User> getFollowersByTrader(Long traderId) {
        return followerToTraderLinkRepository.findAllByTrader(getTraderGlobally(traderId))
                .stream()
                .map(FollowerToTraderLink::getFollower)
                .collect(toList());
    }

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
