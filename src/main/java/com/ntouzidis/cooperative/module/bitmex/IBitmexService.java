package com.ntouzidis.cooperative.module.bitmex;

import com.ntouzidis.cooperative.module.bitmex.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.bitmex.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.user.entity.User;

import java.util.List;
import java.util.Map;

public interface IBitmexService {

    List<Map<String, Object>> get_Announcements(User user, String client);

    Map<String, Object> get_User_Margin(User user, String client);

    List<Map<String, Object>> get_Order_Order_Open(User user, String client);

    Map<String, Object> post_Order_Order(User user, String client, DataPostOrderBuilder dataPostOrderBuilder);

    List<Map<String, Object>> get_Position(User user, String client);

    List<Map<String, Object>> get_Position_Leverage(User user, String client, String data);

    void post_Position_Leverage(User user, String client, DataPostLeverage dataPostLeverage);
}
