package com.ntouzidis.bitmex_trader.module.common.attribute_converters;

import com.ntouzidis.bitmex_trader.module.common.dto.UserDTO;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Base64;

import static java.util.Optional.of;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * This converter is used for encrypting entity attributes, upon the persisting.
 */
@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
  private static final byte[] KEY = "MySuperSecretKey".getBytes();

  @Override
  public String convertToDatabaseColumn(String s) {
    return encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
   return decrypt(s);
  }

  public static UserDTO encryptApiKeys(UserDTO userDTO) {
    return of(userDTO).map(i -> {
      if (userDTO.getApiKey() != null) userDTO.setApiKey(encrypt(userDTO.getApiKey()));
      if (userDTO.getApiSecret() != null) userDTO.setApiSecret(encrypt(userDTO.getApiSecret()));
      return userDTO;
    }).orElseThrow();
  }

  public static UserDTO decryptApiKeys(UserDTO userDTO) {
    return of(userDTO).map(i -> {
      if (userDTO.getApiKey() != null) userDTO.setApiKey(encrypt(userDTO.getApiKey()));
      if (userDTO.getApiSecret() != null) userDTO.setApiSecret(encrypt(userDTO.getApiSecret()));
      return userDTO;
    }).orElseThrow();
  }

  private static String encrypt(String s) {
    if (s == null) return null;
    Key key = new SecretKeySpec(KEY, "AES");
    try {
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(ENCRYPT_MODE, key);
      return Base64.getEncoder().encodeToString(c.doFinal(s.getBytes()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String decrypt(String s) {
    if (s == null) return null;
    Key key = new SecretKeySpec(KEY, "AES");
    try {
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(DECRYPT_MODE, key);
      return new String(c.doFinal(Base64.getDecoder().decode(s)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
