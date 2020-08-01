package com.ntouzidis.bitmex_trader.module.trade.service;

import com.ntouzidis.bitmex_trader.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.pojo.OrderReport;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.bitmex_trader.module.user.entity.User;

import java.util.List;
import java.util.Map;

public interface IBitmexTradeService {

  Map<String, Double> getFollowerBalances(Long traderId);

  Map<String, Double> getFollowerBalances(String traderName);

  OrderReport placeOrderForAll(User trader, int percentage, DataOrderBuilder dataOrder, DataLeverageBuilder dataLeverage);

  OrderReport postOrderWithPercentage(User trader, int percentage, DataOrderBuilder dataOrder);

  List<BitmexOrder> getGuideActiveOrders(User trader);

  List<BitmexPosition> getGuideOpenPositions(User trader);

  Map<String, Double> getBalances();

  void cancelOrder(User trader, DataDeleteOrderBuilder dataDeleteOrder);

  void cancelAllOrders(User trader, DataDeleteOrderBuilder dataDeleteOrder);

  void closeAllPosition(User trader, DataOrderBuilder dataOrder);

  void panicButton(User trader);
}
