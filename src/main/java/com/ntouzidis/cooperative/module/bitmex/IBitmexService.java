package com.ntouzidis.cooperative.module.bitmex;

import java.util.List;
import java.util.Map;

public interface IBitmexService {

    List<Map<String, Object>> get_Announcements(String username, String client);

    Map<String, Object> get_User_Margin(String username, String client);

    List<Map<String, Object>> get_Order_Order_Open(String username, String client);

    Map<String, Object> post_Order_Order(String username, String client, String data);

    List<Map<String, Object>> get_Position(String username, String client);

    List<Map<String, Object>> get_Position_Leverage(String username, String client, String data);

    void post_Position_Leverage(String username, String client, String data);
}
