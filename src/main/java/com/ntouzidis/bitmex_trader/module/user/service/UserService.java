package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.common.forms.AdminForm;
import com.ntouzidis.bitmex_trader.module.common.forms.UserPasswordForm;
import com.ntouzidis.bitmex_trader.module.common.forms.UserUpdateForm;
import com.ntouzidis.bitmex_trader.module.common.forms.UserForm;
import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  Optional<User> findOne(Long id);

  Optional<User> findOne(String username);

  Optional<User> findAdmin(String username);

  Optional<User> findTrader(Long traderId);

  Optional<User> findTrader(String username);

  Optional<User> findFollower(Long id);

  Optional<User> findFollower(String username);

  Optional<User> findOneGlobally(String username);

  User getOne(Long id);

  User getOne(String username);

  User getOneGlobally(String username);

  User getGuideFollower(Long traderId);

  User getGuideFollower(User trader);

  User setGuideFollower(Long traderId, Long followerId);

  Optional<User> getPersonalTrader(User follower);

  User getFollowerOf(User trader, Long followerId);

  User getFollower(Long id);

  User getTrader(Long id);

  User getTrader(String traderName);

  User getAdmin(Long id);

  List<User> getAll();

  List<User> getTraders();

  List<User> getTradersByTenant(Long tenantId);

  List<User> getAdminsByTenant(Long tenantId);

  List<User> getFollowersByTrader(Long traderId);

  List<User> getFollowersByTenant(Long tenantId);

  List<User> getEnabledFollowers(User trader);

  List<User> getNonHiddenFollowers(User trader);

  List<User> getEnabledAndNonHiddenFollowers(User trader);

  User create(UserForm userForm);

  User createAdmin(Tenant tenant, AdminForm adminForm);

  User update(Long userId, UserUpdateForm userUpdateForm);

  User changePassword(User user, UserPasswordForm userPasswordForm);

  User linkTrader(Long followerId, Long traderId);

  void unlinkTrader(User follower);

  void enableOrDisableFollowers(List<User> followers, String status);

  void hideUser(User user);

  User delete(User user);

}
