package com.ntouzidis.cooperative.module.bitmex;

import java.util.Map;

public interface IBitmexService {

    Map<String, Object> getBitmexInfo(String username);

    String getWalletBalance(String username);

    String getAvailableMargin(String username);
}
