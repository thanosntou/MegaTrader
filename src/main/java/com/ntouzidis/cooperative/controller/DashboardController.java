package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.bitmex.IBitmexService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Formatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserService userService;
    private final IBitmexService bitmexService;

    @Autowired
    public DashboardController(UserService userService, IBitmexService bitmexService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
    }

    @GetMapping(value = {"", "/"})
    public String getDashboard(@RequestParam(name="client", required=false, defaultValue = "bitmex") String client,
                               Model model, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUser().getUsername()).orElseThrow(RuntimeException::new);

        Map<String, Object> bitmexUserWalletGet = bitmexService.get_User_Margin(user);
        List<Map<String, Object>> allOrders = bitmexService.get_Order_Order(user);
        List<Map<String, Object>> positions = bitmexService.get_Position(user);

//        List<User> activeTraders = userService.getTraders();
//        User personalTrader = userService.getPersonalTrader(user.getUsername()).orElse(null);

        List<Map<String, Object>> closedOrders = null;
        List<Map<String, Object>> filledOrders = null;
        List<Map<String, Object>> cancelledOrders = null;
        String walletBalance = null;
        String availableMargin = null;
        String activeBalance = null;

        //TODO not sure if .toString() is necessary. if not, remove null check
        if (bitmexUserWalletGet != null) {
            walletBalance = bitmexUserWalletGet.get("walletBalance").toString();
            availableMargin = bitmexUserWalletGet.get("availableMargin").toString();
            activeBalance = String.valueOf(Integer.parseInt(walletBalance) - Integer.parseInt(availableMargin));
        }

        model.addAttribute("page", "dashboard");
        model.addAttribute("user", user);

        model.addAttribute("walletBalance", walletBalance);
        model.addAttribute("balance", walletBalance);
        model.addAttribute("earned", "0");
        model.addAttribute("availableMargin", availableMargin);
        model.addAttribute("activeBalance",activeBalance );
        model.addAttribute("apiKey", user.getApiKey());
        model.addAttribute("apiSecret", user.getApiSecret());
        model.addAttribute("currentClient", "testnet");
        model.addAttribute("closedOrders", closedOrders);
        model.addAttribute("filledOrders", filledOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);
        model.addAttribute("allOrders", allOrders);
        model.addAttribute("positions", positions);

        return "dashboard";
    }

//    private String requestUserDetails(String username) {
//        User principal = userService.findByUsername(username);
//
//        String apikey = principal.getApiKey();
//        String apiSecret = principal.getApiSecret();
//        String expires = String.valueOf(1600883067);
//        String verb = "GET";
//        String path = "/api/v1/user/margin";
//        String data = "";
//
//        String signature = null;
//
//        try {
//            signature = calculateSignature(apiSecret, verb, path, expires, data);
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.set("api-expires", expires);
//        headers.set("api-key", apikey);
//        headers.set("api-signature", signature);
//
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//
//        try {
//            ResponseEntity<?> res = restTemplate.exchange("https://www.bitmex.com" + path, HttpMethod.GET, entity, String.class);
//            return res.getBody().toString();
//        } catch (HttpClientErrorException ex){
//
//        }
//
//        return null;
//
//    }
//
//    private String calculateSignature(String apiSecret, String verb, String path, String expires, String data) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
//        String message = verb + path + expires + data;
//
//        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
//        sha256_HMAC.init(secret_key);
//
//        String resu1 = Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
//
//        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes("UTF-8")));
//
//        return resu1;
//    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }



}
