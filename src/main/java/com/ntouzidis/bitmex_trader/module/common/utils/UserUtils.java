package com.ntouzidis.bitmex_trader.module.common.utils;

import com.ntouzidis.bitmex_trader.module.common.dto.UserDTO;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Role;
import com.ntouzidis.bitmex_trader.module.user.entity.Authority;
import com.ntouzidis.bitmex_trader.module.user.entity.User;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class UserUtils {

  public static boolean isFollower(User user) {
    return user.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals(Role.FOLLOWER.name()));
  }

  public static boolean isTrader(User user) {
    return user.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals(Role.TRADER.name()));
  }

  public static boolean isAdmin(User user) {
    return user.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals(Role.ADMIN.name()));
  }

  public static boolean isRoot(User user) {
    return user.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals(Role.ROOT.name()));
  }

  public static UserDTO toDTO(User user, boolean withAuthorities) {
    if (user == null) return null;

    return UserDTO.builder()
        .id(user.getId())
        .tenant(user.getTenant())
        .username(user.getUsername())
        .password(user.getPassword())
        .email(user.getEmail())
        .enabled(user.getEnabled())
        .apiKey(user.getApiKey())
        .apiSecret(user.getApiSecret())
        .client(user.getClient())
        .createdOn(user.getCreatedOn())
        .authorities(withAuthorities ? user.getAuthorities()
            .stream()
            .map(Authority::getAuthority)
            .collect(toSet()) : null)
        .build();
  }

  public static UserDTO toDTOForFollower(User user) {
    if (user == null) return null;

    return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .build();
  }

  public static UserDTO toDTOForAdmin(User user) {
    if (user == null) return null;

    return UserDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .client(user.getClient())
        .enabled(user.getEnabled())
        .createdOn(user.getCreatedOn())
        .build();
  }

  public static UserDTO toDTOForRoot(User user) {
    if (user == null) return null;

    return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .client(user.getClient())
            .enabled(user.getEnabled())
            .createdOn(user.getCreatedOn())
            .authorities(user.getAuthorities().stream().map(Authority::getAuthority).collect(toSet()))
            .tenant(user.getTenant())
            .build();
  }

  private UserUtils() {
  }
}
