package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.service.BitmexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardApiV1Controller {

    @Value("${baseUrl}")
    private String client;

    private final Context context;
    private final BitmexService bitmexService;

    public DashboardApiV1Controller(Context context, BitmexService bitmexService) {
        this.context = context;
        this.bitmexService = bitmexService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getDashboardInfo() {
//        List<Map<String, Object>> activeOrders;
//        List<Map<String, Object>> openPositions;
        String walletBalance = null;
        String availableMargin = null;
        String activeBalance = null;

        Map<String, Object> bitmexUserWalletGet = bitmexService.getUserMargin(context.getUser());
//        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
//        List<Map<String, Object>> positions = bitmexService.get_Position(user);

        if (!bitmexUserWalletGet.isEmpty()) {
            walletBalance = bitmexUserWalletGet.get("walletBalance").toString();
            availableMargin = bitmexUserWalletGet.get("availableMargin").toString();
            activeBalance = String.valueOf(Integer.parseInt(walletBalance) - Integer.parseInt(availableMargin));
        }

//        activeOrders = allOrders.stream().filter(i -> i.get("ordStatus").equals("New")).collect(Collectors.toList());
//        openPositions = positions.stream().filter(i -> (boolean) i.get("isOpen")).collect(Collectors.toList());

        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("client", client);
        infoMap.put("walletBalance", walletBalance);
        infoMap.put("availableMargin", availableMargin);
        infoMap.put("activeBalance", activeBalance);
        infoMap.put("earned", "0");

        return ResponseEntity.ok(infoMap);
    }
}
