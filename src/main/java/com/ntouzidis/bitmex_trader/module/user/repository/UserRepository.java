package com.ntouzidis.bitmex_trader.module.user.repository;

import com.ntouzidis.bitmex_trader.module.common.pojo.Context;
import com.ntouzidis.bitmex_trader.module.common.utils.UserUtils;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final Context context;
  private final IUserDAO userDAO;

  public List<User> findAll() {
    return userDAO.findAllByTenantId(context.getTenantId())
        .stream()
        .filter(i -> !UserUtils.isRoot(i))
        .collect(toList());
  }

  public List<User> findAllGlobal() {
    return userDAO.findAll()
            .stream()
            .filter(i -> !UserUtils.isRoot(i))
            .collect(toList());
  }

  public List<User> findAllByTenant(Long tenantId) {
    return userDAO.findAllByTenantId(tenantId)
            .stream()
            .filter(i -> !UserUtils.isRoot(i))
            .collect(toList());
  }

  public Optional<User> findOne(Long id) {
    return userDAO.findByTenantIdAndId(context.getTenantId(), id);
  }

  public Optional<User> findOne(String username) {
    return userDAO.findByTenantIdAndUsername(context.getTenantId(), username);
  }

  public Optional<User> findOneGlobal(String username) {
    return userDAO.findByUsername(username);
  }

  public Optional<User> findOneGlobal(Long id) {
    return userDAO.findById(id);
  }

  public User save(User user) {
    return userDAO.save(user);
  }

  public User delete(User user) {
    userDAO.delete(user);
    return user;
  }

}
