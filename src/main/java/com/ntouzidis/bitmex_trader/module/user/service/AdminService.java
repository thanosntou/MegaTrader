package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;

public interface AdminService {

  double calculateTotalVolume(String traderName);

  double calculateActiveVolume(String traderName, Symbol symbol);
}
