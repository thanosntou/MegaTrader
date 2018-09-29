package com.ntouzidis.cooperative.module.bitmex;

import java.util.List;
import java.util.Map;

public interface IBitmexService {

    Map<String, Object> get_User_Margin(String username, String client);

    List get_Order_Order(String username, String client);

    Map<String, Object> post_Order_Order(String username, String client, String data);

    void post_Position_Leverage(String username, String client, String data);
}
