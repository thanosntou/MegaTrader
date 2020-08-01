package com.ntouzidis.bitmex_trader.module.trade.service;

import com.ntouzidis.bitmex_trader.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.bitmex_trader.module.user.entity.User;

import java.util.List;
import java.util.Map;

public interface IBitmexApiService {

  List<BitmexOrder> getActiveOrders(User user);

  List<BitmexPosition> getOpenPositions(User user);

  Map<String, Object> getUserWallet(User user);

  List<Map<String, Object>> getUserWalletHistory(User user);

  List<Map<String, Object>> getUserWalletSummary(User user);

  Map<String, Object> getUserMargin(User user);

  BitmexOrder postOrderOrder(User user, DataOrderBuilder dataOrder);

  Map<String, Object> postPositionLeverage(User user, DataLeverageBuilder dataLeverage);

  String getInstrumentLastPrice(User user, Symbol symbol);

  void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrder);

  void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrder);

  BitmexPosition getSymbolPosition(User user, Symbol symbol);
}
