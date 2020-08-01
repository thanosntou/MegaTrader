package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;

import java.util.List;

public interface TenantService {

  Tenant getOne(Long id);

  Tenant getOne(String name);

  List<Tenant> getAll();

  Tenant create(String name);

  Tenant delete(Long id);
}
