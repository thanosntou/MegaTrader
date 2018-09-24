package com.ntouzidis.cooperative.module.bitmex;

public interface IBitmexService {

    String getWalletBalance(String username);

    String getAvailableMargin(String username);
}
