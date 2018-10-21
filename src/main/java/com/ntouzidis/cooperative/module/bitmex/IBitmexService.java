package com.ntouzidis.cooperative.module.bitmex;

import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.user.entity.User;

import java.util.List;
import java.util.Map;

public interface IBitmexService {

    List<Map<String, Object>> get_Announcements(User user);

    Map<String, Object> get_User_Margin(User user);

    List<Map<String, Object>> get_Order_Order(User user);

    Map<String, Object> post_Order_Order_WithFixeds(User user, DataPostOrderBuilder dataOrder);

    Map<String, Object> post_Order_Order(User user, DataPostOrderBuilder dataPostOrderBuilder);

    void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrderBuilder);

    void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrderBuilder);

    List<Map<String, Object>> get_Position(User user);

    List<Map<String, Object>> get_Position_Leverage(User user, String data);

    void post_Position_Leverage(User user, DataPostLeverage dataPostLeverage);
}
