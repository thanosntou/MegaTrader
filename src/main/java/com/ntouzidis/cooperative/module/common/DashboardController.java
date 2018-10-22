package com.ntouzidis.cooperative.module.common;

import com.ntouzidis.cooperative.module.bitmex.IBitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
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
                               Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("user not found"));

        Map<String, Object> bitmexUserWalletGet = bitmexService.get_User_Margin(user);
        List<Map<String, Object>> positions = bitmexService.get_Position(user);
        List<User> followers = userService.getFollowers(user);

        Object walletBalance = null;
        Object availableMargin = null;
        String activeBalance = null;

        if (bitmexUserWalletGet != null) {
            walletBalance = bitmexUserWalletGet.get("walletBalance");
            availableMargin = bitmexUserWalletGet.get("availableMargin");
            activeBalance = String.valueOf(Integer.parseInt(walletBalance.toString()) - Integer.parseInt(availableMargin.toString()));
        }


        model.addAttribute("user", user);
        model.addAttribute("page", "dashboard");
        model.addAttribute("currentClient", "testnet");

        model.addAttribute("walletBalance", walletBalance);
        model.addAttribute("earned", "0");
        model.addAttribute("availableMargin", availableMargin);
        model.addAttribute("activeBalance",activeBalance );

        model.addAttribute("positions", positions);
        model.addAttribute("followers", followers);

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
