package com.ntouzidis.cooperative.module.trade;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.bitmex.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.bitmex.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private final BitmexService bitmexService;
    private final UserService userService;

    public TradeService(BitmexService bitmexService,
                        UserService userService) {
        this.bitmexService = bitmexService;
        this.userService = userService;
    }

    public void placeOrderForCustomers(String traderUsername, DataPostLeverage dataLeverage, DataPostOrderBuilder dataOrder) {
        List<CustomerToTraderLink> links = userService.getPersonalCustomers(traderUsername);

        links.forEach(link -> {
            bitmexService.post_Position_Leverage(link.getCustomer(), dataLeverage);
            bitmexService.post_Order_Order(link.getCustomer(), dataOrder);
        });
    }
}
