package com.ntouzidis.bitmex_trader.module.common.service;

import com.ntouzidis.bitmex_trader.module.common.dto.UserDTO;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class SimpleEncryptorService {

  private static final String PASSWORD = "kobines";
  private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

  private SimpleEncryptorService() {
    encryptor.setPassword(PASSWORD);
  }

  public String encrypt(String text) {
    return encryptor.encrypt(text);
  }

  public String decrypt(String text) {
    return encryptor.decrypt(text);
  }

  public User encryptApiKeys(User user) {
    if (user.getApiKey() != null) user.setApiKey(encrypt(user.getApiKey()));
    if (user.getApiSecret() != null) user.setApiSecret(encrypt(user.getApiSecret()));
    return user;
  }

  public User decryptApiKeys(User user) {
    if (user.getApiKey() != null) user.setApiKey(decrypt(user.getApiKey()));
    if (user.getApiSecret() != null) user.setApiSecret(decrypt(user.getApiSecret()));
    return user;
  }

  public UserDTO encryptApiKeys(UserDTO userDTO) {
    if (userDTO.getApiKey() != null) userDTO.setApiKey(encrypt(userDTO.getApiKey()));
    if (userDTO.getApiSecret() != null) userDTO.setApiSecret(encrypt(userDTO.getApiSecret()));
    return userDTO;
  }

  public UserDTO decryptApiKeys(UserDTO userDTO) {
    if (userDTO.getApiKey() != null) userDTO.setApiKey(decrypt(userDTO.getApiKey()));
    if (userDTO.getApiSecret() != null) userDTO.setApiSecret(decrypt(userDTO.getApiSecret()));
    return userDTO;
  }
}
