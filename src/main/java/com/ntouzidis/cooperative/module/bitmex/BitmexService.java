package com.ntouzidis.cooperative.module.bitmex;

import com.ntouzidis.cooperative.module.user.User;
import com.ntouzidis.cooperative.module.user.UserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class BitmexService implements IBitmexService {

    @Autowired
    private final UserService userService;

    public BitmexService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Map<String, Object> getBitmexInfo(String username) {
        String res = requestUserDetails(username);

        Map<String, String> info = new HashMap<>();
        if(res != null) {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.toMap();
        }


        return null;
    }

    @Override
    public String getWalletBalance(String username) {
        String res = requestUserDetails(username);

        if(res != null) {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.optString("walletBalance");
        }
        return null;
    }

    @Override
    public String getAvailableMargin(String username) {
        String res = requestUserDetails(username);
        if(res == null) return null;
        JSONObject jsonObj = new JSONObject(res);

        return jsonObj.optString("availableMargin");
    }

    private String requestUserDetails(String username) {
        User principal = userService.findByUsername(username);

        String apikey = principal.getApiKey();
        String apiSecret = principal.getApiSecret();
        String expires = String.valueOf(1600883067);
        String verb = "GET";
        String path = "/api/v1/user/margin";
        String data = "";

        String signature = null;

        try {
            signature = calculateSignature(apiSecret, verb, path, expires, data);

        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("api-expires", expires);
        headers.set("api-key", apikey);
        headers.set("api-signature", signature);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
            ResponseEntity<?> res = restTemplate.exchange("https://www.bitmex.com" + path, HttpMethod.GET, entity, String.class);
            return res.getBody().toString();
        } catch (HttpClientErrorException ex){

        }

        return null;

    }

    private String calculateSignature(String apiSecret, String verb, String path, String expires, String data) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String message = verb + path + expires + data;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        String resu1 = Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));

        return resu1;
    }
}
