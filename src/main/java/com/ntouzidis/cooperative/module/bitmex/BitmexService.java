package com.ntouzidis.cooperative.module.bitmex;

import com.ntouzidis.cooperative.module.user.User;
import com.ntouzidis.cooperative.module.user.UserService;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class BitmexService implements IBitmexService {

    private static String BITMEX_BASE_URL = "https://www.bitmex.com";
    private static String TESTNET_BASE_URL = "https://testnet.bitmex.com";

    private static String ENDPOINT_ORDER = "/api/v1/order";

    private static String ENDPOINT_POSITION_LEVERAGE = "/api/v1/position/leverage";

    private static String ENDPOINT_USER_MARGIN = "/api/v1/user/margin";

    private static String GET = "GET";
    private static String POST = "POST";
    private static String PUT = "PUT";
    private static String DELETE = "DELETE";

    @Autowired
    private final UserService userService;

    public BitmexService(UserService userService) {
        this.userService = userService;
    }

    public List setLeverage(String username, String client, String data) {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(client, "base url");

        String baseUrl = calculateBaseUrl(client);

        String res = request(username, baseUrl, ENDPOINT_POSITION_LEVERAGE, POST, data);

        if(res != null) {
            JSONArray jsonObj = new JSONArray(res);
            return jsonObj.toList();
        }

        return null;

    }

    public Map<String, Object> get_User_Margin(String username, String client) {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(client, "base url");

        String baseUrl = calculateBaseUrl(client);
        String data = "";

        String res = request(username, baseUrl, ENDPOINT_USER_MARGIN, GET, data);

        if(res != null) {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.toMap();
        }

        return null;

    }

    public List get_Order_Order(String username, String client) {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(client, "base url");

        String baseUrl = calculateBaseUrl(client);
        String data = "";

        String res = request(username, baseUrl, ENDPOINT_ORDER, GET, data);

        if(res != null) {
            JSONArray jsonObj = new JSONArray(res);
            return jsonObj.toList();
        }

        return null;

    }

    public Map<String, Object> post_Order_Order(String username, String client, String data) {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(client, "base url");

        String baseUrl = calculateBaseUrl(client);

        String res = requestPOST(username, baseUrl, ENDPOINT_ORDER, data);

        if(res != null) {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.toMap();
        }

        return null;

    }

    private String request(String username, String baseUrl, String path, String verb, String data) {
        User principal = userService.findByUsername(username);

        String apikey = principal.getApiKey();
        String apiSecret = principal.getApiSecret();
        String expires = String.valueOf(1600883067);

        String signature = null;

        try {
            signature = calculateSignature(apiSecret, verb, path, expires, data);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.set("X-Requested-With", "XMLHttpRequest");
            headers.set("api-expires", expires);
            headers.set("api-key", apikey);
            headers.set("api-signature", signature);

            HttpMethod method = null;
            if (verb.equals("GET")) method = HttpMethod.GET;
            if (verb.equals("POST")) method = HttpMethod.POST;
            if (verb.equals("PUT")) method = HttpMethod.PUT;
            if (verb.equals("DELETE")) method = HttpMethod.DELETE;

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<?> res = restTemplate.exchange(baseUrl + path, HttpMethod.POST, entity, String.class);
            return Objects.requireNonNull(res.getBody()).toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException | HttpClientErrorException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
    private String requestPOST(String username, String baseUrl, String path, String data) {
        User principal = userService.findByUsername(username);

        String verb = "POST";
        String apikey = principal.getApiKey();
        String apiSecret = principal.getApiSecret();
        String expires = String.valueOf(1600883067);

        String signature = null;

        try {
            signature = calculateSignature(apiSecret, verb, path, expires, data);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.set("X-Requested-With", "XMLHttpRequest");
            headers.set("api-expires", expires);
            headers.set("api-key", apikey);
            headers.set("api-signature", signature);

//            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//            body.add("raw", data);

            HttpEntity<String> entity = new HttpEntity<>(data, headers);

            ResponseEntity<?> res = restTemplate.exchange(baseUrl + path, HttpMethod.POST, entity, String.class);
            return Objects.requireNonNull(res.getBody()).toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException | HttpClientErrorException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String calculateSignature(String apiSecret, String verb, String path, String expires, String data) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Preconditions.checkNotNull(apiSecret, "API Secret");
        Preconditions.checkNotNull(verb, "request method");
        Preconditions.checkNotNull(path, "bitmex path");
        Preconditions.checkNotNull(expires, "expire seconds");
        Preconditions.checkNotNull(data, "request data");

        String message = verb + path + expires + data;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        String resu1 = Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
//        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));

        return resu1;
    }

    private String calculateBaseUrl(String client) {
        String baseUrl = null;

        if (client.equalsIgnoreCase("bitmex"))
            baseUrl = BITMEX_BASE_URL;
        if(client.equalsIgnoreCase("testnet"))
            baseUrl = TESTNET_BASE_URL;

        return baseUrl;
    }
}
