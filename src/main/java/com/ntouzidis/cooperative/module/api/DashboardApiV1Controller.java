package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.bitmex.IBitmexService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    private final IBitmexService bitmexService;

    public DashboardApiV1Controller(IBitmexService bitmexService) {
        this.bitmexService = bitmexService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, String>> getDashboardInfo(Authentication authentication) {

        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

//        List<Map<String, Object>> activeOrders;
//        List<Map<String, Object>> openPositions;
        String walletBalance = null;
        String availableMargin = null;
        String activeBalance = null;

        Map<String, Object> bitmexUserWalletGet = bitmexService.get_User_Margin(user);
//        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
//        List<Map<String, Object>> positions = bitmexService.get_Position(user);

        if (!bitmexUserWalletGet.isEmpty()) {
            walletBalance = bitmexUserWalletGet.get("walletBalance").toString();
            availableMargin = bitmexUserWalletGet.get("availableMargin").toString();
            activeBalance = String.valueOf(Integer.parseInt(walletBalance.toString()) - Integer.parseInt(availableMargin.toString()));
        }

//        activeOrders = allOrders.stream().filter(i -> i.get("ordStatus").equals("New")).collect(Collectors.toList());
//        openPositions = positions.stream().filter(i -> (boolean) i.get("isOpen")).collect(Collectors.toList());

        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("client", client);
        infoMap.put("walletBalance", walletBalance);
        infoMap.put("availableMargin", availableMargin);
        infoMap.put("activeBalance", activeBalance);
        infoMap.put("earned", "0");

        return new ResponseEntity<>(infoMap, HttpStatus.OK);
    }
}
