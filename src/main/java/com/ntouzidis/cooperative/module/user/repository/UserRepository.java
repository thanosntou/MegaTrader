package com.ntouzidis.cooperative.module.user.repository;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

  private final Context context;
  private final UserDAO userDAO;
  private final SimpleEncryptor simpleEncryptor;

  public UserRepository(Context context, UserDAO userDAO, SimpleEncryptor simpleEncryptor) {
    this.context = context;
    this.userDAO = userDAO;
    this.simpleEncryptor = simpleEncryptor;
  }

  public List<User> findAll() {
    return userDAO.findAllByTenantId(context.getTenantId());
  }

  public List<User> findAllGlobal(Integer tenandId) {
    return userDAO.findAllByTenantId(tenandId);
  }

  public Optional<User> findOne(Integer id) {
    return userDAO.findByTenantIdAndId(context.getTenantId(), id);
  }

  public Optional<User> findOne(String username) {
    return userDAO.findByTenantIdAndUsername(context.getTenantId(), username);
  }

  public Optional<User> findOneGlobal(String username) {
    return userDAO.findByUsername(username);
  }

  public void update(User user) {
    userDAO.save(user);
  }

  public void save(User user) {
    user.setCreate_date();
    userDAO.save(encryptUserApiKeys(user));
  }

  public void delete(User user) {
    userDAO.delete(user);
  }

  private User encryptUserApiKeys(User user) {
    if (user.getApiKey() != null)
      user.setApiKey(simpleEncryptor.encrypt(user.getApiKey()));
    if (user.getApiSecret() != null)
      user.setApiSecret(simpleEncryptor.encrypt(user.getApiSecret()));
    return user;
  }

}
