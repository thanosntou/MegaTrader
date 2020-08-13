package com.ntouzidis.bitmex_trader.module.user.service;

import com.google.common.collect.ImmutableSet;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Role;
import com.ntouzidis.bitmex_trader.module.common.forms.AdminForm;
import com.ntouzidis.bitmex_trader.module.common.forms.UserForm;
import com.ntouzidis.bitmex_trader.module.common.forms.UserPasswordForm;
import com.ntouzidis.bitmex_trader.module.common.forms.UserUpdateForm;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import com.ntouzidis.bitmex_trader.module.user.entity.*;
import com.ntouzidis.bitmex_trader.module.user.repository.FollowerToTraderLinkRepository;
import com.ntouzidis.bitmex_trader.module.user.repository.LoginRepository;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Client;
import com.ntouzidis.bitmex_trader.module.common.pojo.Context;
import com.ntouzidis.bitmex_trader.module.common.utils.UserUtils;
import com.ntouzidis.bitmex_trader.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.ntouzidis.bitmex_trader.module.common.constants.GlobalConstants.DISABLE;
import static com.ntouzidis.bitmex_trader.module.common.constants.GlobalConstants.ENABLE;
import static com.ntouzidis.bitmex_trader.module.common.constants.MessagesConstants.*;
import static com.ntouzidis.bitmex_trader.module.common.enumeration.Client.TESTNET;
import static com.ntouzidis.bitmex_trader.module.common.enumeration.Role.*;
import static com.ntouzidis.bitmex_trader.module.common.utils.UserUtils.*;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final Context context;
  private final UserRepository userRepository;
  private final LoginRepository loginRepository;
  private final PasswordEncoder passwordEncoder;
  private final FollowerToTraderLinkRepository followerToTraderLinkRepository;

  @Override
  public Optional<User> findOne(Long id) {
    return userRepository.findOne(id);
  }

  @Override
  public Optional<User> findOne(String username) {
    return userRepository.findOne(username);
  }

  @Override
  public Optional<User> findAdmin(String username) {
    return findOne(username).filter(UserUtils::isAdmin);
  }

  @Override
  public Optional<User> findTrader(Long traderId) {
    return findOne(traderId).filter(UserUtils::isTrader);
  }

  @Override
  public Optional<User> findTrader(String username) {
    return findOne(username).filter(UserUtils::isTrader);
  }


  @Override
  public Optional<User> findFollower(Long id) {
    return findOne(id).filter(UserUtils::isFollower);
  }

  @Override
  public Optional<User> findFollower(String username) {
    return findOne(username).filter(UserUtils::isFollower);
  }

  @Override
  public Optional<User> findOneGlobally(String username) {
    return userRepository.findOneGlobal(username);
  }

  @Override
  public User getOne(Long id) {
    return findOne(id).orElseThrow(() -> new NotFoundException(format(USER_NOT_FOUND, id)));
  }

  @Override
  public User getOne(String username) {
    return findOne(username).orElseThrow(() -> new NotFoundException(format(USER_NOT_FOUND, username)));
  }

  @Override
  public User getOneGlobally(String username) {
    return findOneGlobally(username).orElseThrow(() -> new NotFoundException(format(USER_NOT_FOUND, username)));
  }

  @Override
  public User getGuideFollower(Long traderId) {
    return followerToTraderLinkRepository.findAllByTraderAndGuide(getTrader(traderId), true)
        .stream()
        .findFirst()
        .orElseThrow(() -> new NotFoundException("Guide follower not found"))
        .getFollower();
  }

  @Override
  public User getGuideFollower(User trader) {
    return followerToTraderLinkRepository.findAllByTraderAndGuide(trader, true)
            .stream()
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Guide follower not found"))
            .getFollower();
  }

  @Override
  @Transactional
  public User setGuideFollower(Long traderId, Long followerId) {
    List<FollowerToTraderLink> links = followerToTraderLinkRepository.findAllByTrader(getTrader(traderId));

    links.stream()
            .filter(FollowerToTraderLink::getGuide)
            .findFirst()
            .ifPresent(i -> {
              i.setGuide(false);
              followerToTraderLinkRepository.save(i);
            });

    return links.stream()
            .filter(i -> i.getFollower().getId().equals(followerId))
            .findFirst()
            .map(f -> {
              f.setGuide(true);
              return followerToTraderLinkRepository.save(f).getFollower();
            })
            .orElseThrow(() -> new NotFoundException(format(FOLLOWER_NOT_FOUND_BY_ID, followerId)));
  }

  @Override
  public Optional<User> getPersonalTrader(User follower) {
    return followerToTraderLinkRepository.findByFollower(follower)
        .map(FollowerToTraderLink::getTrader);
  }

  @Override
  public User getFollowerOf(User trader, Long id) {
    return followerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .map(FollowerToTraderLink::getFollower)
        .filter(i -> i.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new NotFoundException(FOLLOWER_NOT_FOUND));
  }

  @Override
  public User getFollower(Long id) {
    return findFollower(id).orElseThrow(() -> new NotFoundException(FOLLOWER_NOT_FOUND));
  }

  @Override
  public User getTrader(Long traderId) {
    return findTrader(traderId).orElseThrow(() -> new NotFoundException(format(TRADER_NOT_FOUND_BY_ID, traderId)));
  }

  @Override
  public User getTrader(String traderName) {
    return findOne(traderName).filter(UserUtils::isTrader).orElseThrow(() -> new NotFoundException(TRADER_NOT_FOUND));
  }

  @Override
  public User getAdmin(Long id) {
    return findOne(id).filter(UserUtils::isAdmin).orElseThrow(() -> new NotFoundException(ADMIN_NOT_FOUND_BY_ID));
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public List<User> getAllGlobal() {
    return userRepository.findAllGlobal();
  }

  @Override
  public List<User> getTraders() {
    return userRepository.findAll()
        .stream()
        .filter(UserUtils::isTrader)
        .collect(toList());
  }

  @Override
  public List<User> getTradersByTenant(Long tenantId) {
    return userRepository.findAllByTenant(tenantId).stream()
            .filter(UserUtils::isTrader)
            .collect(toList());
  }

  @Override
  public List<User> getAdminsByTenant(Long tenantId) {
    return userRepository.findAllByTenant(tenantId)
            .stream()
            .filter(UserUtils::isAdmin)
            .collect(toList());
  }

  @Override
  public List<User> getFollowersByTrader(Long traderId) {
    return followerToTraderLinkRepository.findAllByTrader(getTrader(traderId))
        .stream()
        .map(FollowerToTraderLink::getFollower)
        .collect(toList());
  }

  @Override
  public List<User> getFollowersByTenant(Long tenantId) {
    return userRepository.findAllByTenant(tenantId).stream()
            .filter(UserUtils::isFollower)
            .collect(toList());
  }

  @Override
  public List<User> getEnabledFollowers(User trader) {
    return followerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .map(FollowerToTraderLink::getFollower)
        .filter(User::getEnabled)
        .collect(toList());
  }

  @Override
  public List<User> getNonHiddenFollowers(User trader) {
    return followerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .filter(link -> !link.getIsHidden())
        .map(FollowerToTraderLink::getFollower)
        .collect(toList());
  }

  @Override
  public List<User> getEnabledAndNonHiddenFollowers(User trader) {
    return followerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .filter(link -> !link.getIsHidden())
        .map(FollowerToTraderLink::getFollower)
        .filter(User::getEnabled)
        .collect(toList());
  }

  @Override
  public User signUp(UserForm form) {
    Role role = form.getRole();
    String username = form.getUsername();
    String email = form.getEmail();
    String pass = form.getPass();
    String confirmPass = form.getConfirmPass();
    String refName = form.getReferer();

    User referer = findOneGlobally(refName).orElseThrow(() ->
        new NotFoundException(format("Referer %s not found", refName)));

    checkArgument(isFollower(referer) || isTrader(referer) || isAdmin(referer),
        format("Referer %s not found", refName));

    checkArgument(pass.equals(confirmPass), PASSWORD_NOT_MATCH);

    return createUser(referer.getTenant(), username, pass, email, ImmutableSet.of(new Authority(role)));
  }

  @Override
  @Transactional
  public User createAdmin(Tenant tenant, AdminForm form) {
    String username = form.getUsername();
    String pass = form.getPass();
    String confirmPass = form.getConfirmPass();
    String email = form.getEmail();

    checkArgument(pass.equals(confirmPass), PASSWORD_NOT_MATCH);

    return createUser(tenant, username, pass, email, ImmutableSet.of(new Authority(ADMIN)));
  }

  @Override
  public User createTrader(Tenant tenant, AdminForm form) {
    String username = form.getUsername();
    String pass = form.getPass();
    String confirmPass = form.getConfirmPass();
    String email = form.getEmail();

    checkArgument(pass.equals(confirmPass), PASSWORD_NOT_MATCH);

    return createUser(tenant, username, pass, email, ImmutableSet.of(new Authority(TRADER)));
  }

  @Override
  public User createFollower(Tenant tenant, AdminForm form) {
    String username = form.getUsername();
    String pass = form.getPass();
    String confirmPass = form.getConfirmPass();
    String email = form.getEmail();

    checkArgument(pass.equals(confirmPass), PASSWORD_NOT_MATCH);

    return createUser(tenant, username, pass, email, ImmutableSet.of(new Authority(FOLLOWER)));
  }

  public User createUser(Tenant tenant, String username, String pass, String email, Set<Authority> authorities) {
    checkArgument(!usernameExistsGlobally(username), "username exists");

    User user = new User(username, passwordEncoder.encode(pass));
    user.setClient(TESTNET);
    user.setTenant(tenant);
    user.setEmail(email);
    user.setEnabled(false);
    user.setAuthorities(new ArrayList<>());
    user.setQtyPreferences(new ArrayList<>());

    authorities.forEach(user::addAuthority);

    EnumSet.allOf(Symbol.class).forEach(symbol -> user.addQtyPreference(new QtyPreference(user, symbol, 0)));

    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User update(Long userId, UserUpdateForm form) {
      User user = getOne(userId);

      Client client = form.getClient();

      ofNullable(form.getSymbol()).ifPresent(s ->
          ofNullable(form.getQty()).ifPresent(q ->
              user.getQtyPreference(s).setValue(q)));

      ofNullable(client).ifPresent(user::setClient);
      ofNullable(form.getApiKey()).ifPresent(user::setApiKey);
      ofNullable(form.getApiSecret()).ifPresent(user::setApiSecret);

      return userRepository.save(user);
  }

  @Override
  @Transactional
  public User changePassword(User user, UserPasswordForm form) {
    String newPass = form.getNewPass();
    String confirmPass = form.getConfirmPass();

    checkArgument(newPass.equals(confirmPass), "Wrong confirmation password");

    user.setPassword(passwordEncoder.encode(newPass));
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User linkTrader(Long followerId, Long traderId) {
    User trader = getTrader(traderId);
    User follower = getFollower(followerId);

    FollowerToTraderLink link = new FollowerToTraderLink();
    link.setFollower(follower);
    link.setTrader(trader);
    link.setGuide(false);
    link.setIsHidden(false);

    followerToTraderLinkRepository.save(link);
    follower.setEnabled(false);
    userRepository.save(follower);
    return trader;
  }

  @Override
  @Transactional
  public void unlinkTrader(User follower) {
    FollowerToTraderLink followerToTraderLink = followerToTraderLinkRepository.findByFollower(follower)
        .orElseThrow(() -> new NotFoundException(TRADER_NOT_FOUND));

    followerToTraderLinkRepository.delete(followerToTraderLink);
  }

  @Override
  @Transactional
  public void enableOrDisableFollowers(List<User> followers, String status) {
    if (followers.isEmpty())
      return;

    if (DISABLE.equalsIgnoreCase(status)) {
      followers.forEach(follower -> {
        follower.setEnabled(false);
        userRepository.save(follower);
      });
    } else if (ENABLE.equalsIgnoreCase(status)) {
      followers.forEach(follower -> {
        follower.setEnabled(true);
        userRepository.save(follower);
      });
    }
  }

  @Override
  @Transactional
  public void hideUser(User user) {
    followerToTraderLinkRepository.findByFollower(user).ifPresent(i -> {
      i.setIsHidden(true);
      followerToTraderLinkRepository.save(i);
    });
  }

  @Override
  @Transactional
  public User delete(User user) {
    loginRepository.deleteAllByUser(user);
    return userRepository.delete(user);
  }


  private boolean usernameExistsGlobally(String username) {
    return userRepository.findOneGlobal(username).isPresent();
  }

}
