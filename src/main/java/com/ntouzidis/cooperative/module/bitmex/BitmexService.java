package com.ntouzidis.cooperative.module.bitmex;

import com.ntouzidis.cooperative.module.user.User;
import com.ntouzidis.cooperative.module.user.UserService;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BitmexService implements IBitmexService {

    private static String BITMEX_BASE_URL = "https://www.bitmex.com";
    private static String TESTNET_BASE_URL = "https://testnet.bitmex.com";

    private static String ENDPOINT_ANNOUNCEMENT = "/api/v1/announcement";
    private static String ENDPOINT_ORDER = "/api/v1/order";
    private static String ENDPOINT_POSITION = "/api/v1/position";
    private static String ENDPOINT_POSITION_LEVERAGE = "/api/v1/position/leverage";
    private static String ENDPOINT_USER_MARGIN = "/api/v1/user/margin";

    private static String GET = "GET";
    private static String POST = "POST";
    private static String PUT = "PUT";
    private static String DELETE = "DELETE";

    private final UserService userService;

    public BitmexService(UserService userService) {
        this.userService = userService;
    }

    public List<Map<String, Object>> get_Announcements(String username, String client) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);
        String res = requestGET(username, baseUrl, ENDPOINT_ANNOUNCEMENT, "");

        return getMapList(res);

    }


    public Map<String, Object> get_User_Margin(String username, String client) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);
        String data = "";

        String res = requestGET(username, baseUrl, ENDPOINT_USER_MARGIN, data);

        return getMap(res);

    }

    public List<Map<String, Object>> get_Order_Order(String username, String client) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);
        String data = "";
        String path = ENDPOINT_ORDER + "?reverse=true";

        String res = requestGET(username, baseUrl, path, data);

        return getMapList(res);

    }

    public List<Map<String, Object>> get_Order_Order_Open(String username, String client) {
        checkPreconditions(username, client);

        List<Map<String, Object>> myMapList = get_Order_Order(username, client);
        List<Map<String, Object>> filteredMapList = myMapList.stream().filter(map -> map.get("ordStatus").equals("New")).collect(Collectors.toList());
        return filteredMapList;

    }

    public Map<String, Object> post_Order_Order(String username, String client, String data) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);
        String res = requestPOST(username, baseUrl, ENDPOINT_ORDER, data);

        if(res != null) {
            JSONObject jsonObj = new JSONObject(res);
            return jsonObj.toMap();
        }

        return null;
    }

    public List<Map<String, Object>> get_Position(String username, String client) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);
        String res = requestGET(username, baseUrl, ENDPOINT_POSITION, "");

        return getMapList(res);
    }

    public List<Map<String, Object>> get_Position_Leverage(String username, String client, String data) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);
        String res = requestGET(username, baseUrl, ENDPOINT_POSITION_LEVERAGE, data);

        return getMapList(res);
    }

    public void post_Position_Leverage(String username, String client, String data) {
        checkPreconditions(username, client);

        String baseUrl = calculateBaseUrl(client);

        requestPOST(username, baseUrl, ENDPOINT_POSITION_LEVERAGE, data);
    }

    private String requestGET(String username, String baseUrl, String path, String data) {
        User principal = userService.findByUsername(username);

        String apikey = principal.getApiKey();
        String apiSecret = principal.getApiSecret();
        String expires = String.valueOf(1600883067);
        String signature;

        try {
            signature = calculateSignature(apiSecret, GET, path, expires, data);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Content-type", "application/x-www-form-urlencoded");
            headers.set("X-Requested-With", "XMLHttpRequest");
            headers.set("api-expires", expires);
            headers.set("api-key", apikey);
            headers.set("api-signature", signature);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<?> res = restTemplate.exchange(baseUrl + path, HttpMethod.GET, entity, String.class);
            return Objects.requireNonNull(res.getBody()).toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException | HttpClientErrorException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

//    private String requestGET2(String username, String baseUrl, String path, String data) {
//        User principal = userService.findByUsername(username);
//        String expires = String.valueOf(1600883067);
//
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Accept", "application/json");
//            headers.set("Content-type", "application/x-www-form-urlencoded");
//            headers.set("X-Requested-With", "XMLHttpRequest");
//            headers.set("api-expires", expires);
//            headers.set("api-key", principal.getApiKey());
//            headers.set("api-signature", calculateSignature(principal.getApiSecret(), GET, path, expires, data));
//
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<String> res = restTemplate.exchange(baseUrl + "/api/v1/order?filter={key}", HttpMethod.GET, entity, String.class, "%7B%22open%22%3A%20true%7D");
//            return Objects.requireNonNull(res.getBody());
//
//        } catch (NoSuchAlgorithmException | InvalidKeyException | HttpClientErrorException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    private String requestGET3(String username, String baseUrl, String path, String data) {
//        User principal = userService.findByUsername(username);
//
//        String apikey = principal.getApiKey();
//        String apiSecret = principal.getApiSecret();
//        String expires = String.valueOf(1600883067);
//        String verb = "GET";
//
//        try {
////            ResponseEntity<String> response
////                    = restTemplate.getForEntity(fooResourceUrl + "/1", String.class, builder);
//
//            String signature = calculateSignature(apiSecret, verb, path, expires, data);
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Accept", "application/json;charset=UTF-8");
//            headers.set("Content-Type", "application/x-www-form-urlencoded");
//            headers.set("api-expires", expires);
//            headers.set("api-key", apikey);
//            headers.set("api-signature", signature);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.set("filter", "{\"open\": true}");
//
//            UriComponentsBuilder builder = UriComponentsBuilder
//                    .fromHttpUrl(baseUrl + "/api/v1/order").queryParams(params);
////                    .queryParam("filter", "{\"open\": true}");
//
//            String encoded1 = builder.toUriString();
//            String encoded2 = builder.build().toUri().toString();
//
//            String url = baseUrl + ENDPOINT_ORDER + "?filter={value}";
//            URI uri = UriComponentsBuilder.fromUriString(url).build().encode().toUri();
//
//            String encoded = baseUrl + ENDPOINT_ORDER + "?filter=" + URLEncoder.encode("{\"open\": true}", StandardCharsets.UTF_8.name());
//
//            HttpEntity<?> entity = new HttpEntity<>(headers);
//
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<String> response = restTemplate.exchange(encoded2, HttpMethod.GET, entity, String.class);
//
//            return Objects.requireNonNull(response.getBody());
//
//        } catch (NoSuchAlgorithmException | InvalidKeyException | HttpClientErrorException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

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

    private Map<String, Object> getMap(String responseBody) {
        if(responseBody != null) {
            JSONObject jsonObj = new JSONObject(responseBody);
            return jsonObj.toMap();
        }
        return null;
    }

    private List<Map<String, Object>> getMapList(String responseBody) {
        if(responseBody != null) {
            JSONArray jsonArray = new JSONArray(responseBody);
            List<Map<String, Object>> myMapList = new ArrayList<>();

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                myMapList.add(jsonObj.toMap());
            }
            return myMapList;
        }
        return null;
    }

    private void checkPreconditions(String username, String client) {
        Preconditions.checkNotNull(username, "username");
        Preconditions.checkNotNull(client, "base url");
    }
}
