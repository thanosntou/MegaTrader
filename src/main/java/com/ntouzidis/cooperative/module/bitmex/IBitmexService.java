package com.ntouzidis.cooperative.module.bitmex;

import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.user.entity.User;

import java.util.List;
import java.util.Map;

public interface IBitmexService {

    List<Map<String, Object>> get_Announcements(User user);

    String getInstrumentLastPrice(User user, Symbol symbol);

    Map<String, Object> getUserWallet(User user);

    Map<String, Object> get_User_Margin(User user);

    List<Map<String, Object>> get_Order_Order(User user);

    Map<String, Object> post_Order_Order_WithFixedsAndPercentage(User user, DataPostOrderBuilder dataOrder, int percentage);

    Map<String, Object> post_Order_Order_WithFixeds(User user, DataPostOrderBuilder dataOrder, String leverage);

    Map<String, Object> post_Order_Order(User user, DataPostOrderBuilder dataPostOrderBuilder);

    void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrderBuilder);

    void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrderBuilder);

    Map<String, Object> getSymbolPosition(User user, Symbol symbol);

    List<Map<String, Object>> getAllSymbolPosition(User user);

    List<Map<String, Object>> get_Position(User user);

    List<Map<String, Object>> get_Position_Leverage(User user, String data);

    Map<String, Object> post_Position_Leverage(User user, DataPostLeverage dataPostLeverage);
}
