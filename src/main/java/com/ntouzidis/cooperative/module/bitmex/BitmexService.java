package com.ntouzidis.cooperative.module.bitmex;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.api.UserApiV1Controller;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.endpoints.InstrumentEndpoint;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class BitmexService implements IBitmexService {

    Logger logger = LoggerFactory.getLogger(BitmexService.class);

    private static String ENDPOINT_ANNOUNCEMENT = "/api/v1/announcement";
    private static String ENDPOINT_ORDER = "/api/v1/order";
    private static String ENDPOINT_ORDER_ALL = "/api/v1/order/all";
    private static String ENDPOINT_POSITION = "/api/v1/position";
    private static String ENDPOINT_POSITION_LEVERAGE = "/api/v1/position/leverage";
    private static String ENDPOINT_USER_MARGIN = "/api/v1/user/margin";
    private static String ENDPOINT_USER_WALLET = "/api/v1/user/wallet";

    private static String GET = "GET";
    private static String POST = "POST";
    private static String PUT = "PUT";
    private static String DELETE = "DELETE";

    @Value("${baseUrl}")
    private String base_url;

    private final SimpleEncryptor simpleEncryptor;

    public BitmexService(SimpleEncryptor simpleEncryptor) {
        this.simpleEncryptor = simpleEncryptor;
    }

    @Override
    public List<Map<String, Object>> get_Announcements(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_ANNOUNCEMENT, "");

        return getMapList(res.orElse(null));
    }

    @Override
    public String getInstrumentLastPrice(User user, Symbol symbol) {
        try {
            Preconditions.checkNotNull(user, "user cannot be null");
            Preconditions.checkNotNull(symbol, "symbol cannot be null");

            Optional<String> res = requestGET(user, InstrumentEndpoint.INSTRUMENT + "?symbol=" + symbol.getValue(), "");

            List<Map<String, Object>> ml = getMapList(res.orElse(null));

            Preconditions.checkState(symbol.getValue().equals(ml.get(0).get("symbol")));

            return ml.get(0).get("lastPrice").toString();
        } catch (Exception e) {
            logger.error("instrument last price calculation failed");
        }
        return "0";
    }

    @Override
    public Map<String, Object> getUserWallet(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_USER_WALLET, "");

        return getMap(res.orElse(null));
    }

    @Override
    public Map<String, Object> get_User_Margin(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_USER_MARGIN, "");

        return getMap(res.orElse(null));
    }

    @Override
    public List<Map<String, Object>> get_Order_Order(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_ORDER + "?reverse=true", "");

        return getMapList(res.orElse(null));
    }

    @Override
    public Map<String, Object> post_Order_Order_WithFixedsAndPercentage(User user, DataPostOrderBuilder dataOrder, int percentage) {
        Preconditions.checkNotNull(user, "user cannot be null");

        long qty = 0L;
        Map<String, Object> position = get_Position(user)
                .stream()
                .filter(i -> i.get("symbol").equals(dataOrder.getSymbol()))
                .findAny()
                .orElse(Collections.emptyMap());

        if (!position.isEmpty())
            qty = Long.valueOf(position.get("currentQty").toString());

        Long finalQty = qty * percentage / 100;

        Optional<String> res = requestPOST(user, ENDPOINT_ORDER, dataOrder.withOrderQty(finalQty.toString()).get());

        return getMap(res.orElse(null));
    }

    @Override
    public Map<String, Object> post_Order_Order_WithFixeds(User user, DataPostOrderBuilder dataOrder, String leverage) {
        Preconditions.checkNotNull(user, "user cannot be null");

//        if (dataOrder.getOrderQty() == null)
//            dataOrder.withOrderQty(calculateFixedQtyForSymbol(user, dataOrder.getSymbol(), leverage));

        Optional<String> res = requestPOST(user, ENDPOINT_ORDER, dataOrder.get());

        return getMap(res.orElse(null));
    }

    @Override
    public Map<String, Object> post_Order_Order(User user, DataPostOrderBuilder dataOrder) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestPOST(user, ENDPOINT_ORDER, dataOrder.get());

        return getMap(res.orElse(null));
    }

    @Override
    public void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrder) {
        requestDELETE(user, ENDPOINT_ORDER, dataDeleteOrder.get());
    }

    @Override
    public void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrderBuilder) {
        requestDELETE(user, ENDPOINT_ORDER_ALL, dataDeleteOrderBuilder.get());
    }

    @Override
    public Map<String, Object> getSymbolPosition(User user, Symbol symbol) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_POSITION, "");

        return getMapList(res.orElse(null)).stream().filter(i -> i.get("symbol").equals(symbol.getValue())).findAny().orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAllSymbolPosition(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_POSITION, "");

        return getMapList(res.orElse(null));
    }

    @Override
    public List<Map<String, Object>> get_Position(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_POSITION, "");

        return getMapList(res.orElse(null));
    }

    @Override
    public List<Map<String, Object>> get_Position_Leverage(User user, String data) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_POSITION_LEVERAGE, data);

        return getMapList(res.orElse(null));
    }

    @Override
    public Map<String, Object> post_Position_Leverage(User user, DataPostLeverage dataLeverageBuilder) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestPOST(user, ENDPOINT_POSITION_LEVERAGE, dataLeverageBuilder.get());

        return getMap(res.orElse(null));
    }

    private Optional<String> requestGET(User user, String path, String data) {
        String apikey;
        String apiSecret;
        String expires;
        String signature;

        try {
            Preconditions.checkState(user.getClient() != null, "User has not set a client");

            long start = System.nanoTime();
            apikey = simpleEncryptor.decrypt(user.getApiKey());
            long end = System.nanoTime();

            System.out.println((end - start) / 1000000);
            apiSecret = simpleEncryptor.decrypt(user.getApiSecret());
            expires = String.valueOf(1600883067);
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

            ResponseEntity<?> res = restTemplate.exchange(user.getClient().getValue() + path, HttpMethod.GET, entity, String.class);

            return Optional.ofNullable(Objects.requireNonNull(res.getBody()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Optional<String> requestPOST(User user, String path, String data) {
        String apikey;
        String apiSecret;
        String expires;
        String signature;

        try {
            Preconditions.checkState(user.getClient() != null, "User has not set a client");

            apikey = simpleEncryptor.decrypt(user.getApiKey());
            apiSecret = simpleEncryptor.decrypt(user.getApiSecret());
            expires = String.valueOf(1600883067);
            signature = calculateSignature(apiSecret, POST, path, expires, data);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("X-Requested-With", "XMLHttpRequest");
            headers.set("api-expires", expires);
            headers.set("api-key", apikey);
            headers.set("api-signature", signature);

//            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
//            body.add("raw", data);

            HttpEntity<String> entity = new HttpEntity<>(data, headers);

            ResponseEntity<?> res = restTemplate.exchange(user.getClient().getValue() + path, HttpMethod.POST, entity, String.class);

            return Optional.ofNullable(Objects.requireNonNull(res.getBody()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void requestDELETE(User user, String path, String data) {
        String apikey;
        String apiSecret;
        String expires;
        String signature;

        try {
            Preconditions.checkState(user.getClient() != null, "User has not set a client");

            apikey = simpleEncryptor.decrypt(user.getApiKey());
            apiSecret = simpleEncryptor.decrypt(user.getApiSecret());
            expires = String.valueOf(1600883067);

            signature = calculateSignature(apiSecret, DELETE, path, expires, data);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("api-expires", expires);
            headers.set("api-key", apikey);
            headers.set("api-signature", signature);

            HttpEntity<String> entity = new HttpEntity<>(data, headers);

            ResponseEntity<?> res = restTemplate.exchange(user.getClient().getValue() + path, HttpMethod.DELETE, entity, String.class);

            Objects.requireNonNull(res.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }

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
//            ResponseEntity<String> response
//                    = restTemplate.getForEntity(fooResourceUrl + "/1", String.class, builder);
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
//                    .queryParam("filter", "{\"open\": true}");
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

//    private String calculateFixedQtyForSymbol(User user, String symbol, String leverage) {
//        if (symbol.equals(Symbol.XBTUSD.getValue()))
//            return calculateOrderQty(user, Symbol.XBTUSD, user.getFixedQtyXBTUSD(), leverage);
//        if (symbol.equals(Symbol.ETHUSD.getValue()))
//            return calculateOrderQtyETHUSD(user, user.getFixedQtyETHUSD(), leverage);
//        if (symbol.equals(Symbol.ADAXXX.getValue()))
//            return calculateOrderQty(user, Symbol.ADAXXX, user.getFixedQtyADAZ18(), leverage);
//        if (symbol.equals(Symbol.BCHXXX.getValue()))
//            return calculateOrderQty(user, Symbol.BCHXXX, user.getFixedQtyBCHZ18(), leverage);
//        if (symbol.equals(Symbol.EOSXXX.getValue()))
//            return calculateOrderQty(user, Symbol.EOSXXX, user.getFixedQtyEOSZ18(), leverage);
//        if (symbol.equals(Symbol.ETHXXX.getValue()))
//            return calculateOrderQty(user, Symbol.ETHXXX, user.getFixedQtyXBTJPY(), leverage);
//        //TODO fix these ethh19
//        if (symbol.equals(Symbol.LTCXXX.getValue()))
//            return calculateOrderQty(user, Symbol.LTCXXX, user.getFixedQtyLTCZ18(), leverage);
//        if (symbol.equals(Symbol.TRXXXX.getValue()))
//            return calculateOrderQty(user, Symbol.TRXXXX, user.getFixedQtyTRXZ18(), leverage);
//        if (symbol.equals(Symbol.XRPXXX.getValue()))
//            return calculateOrderQty(user, Symbol.XRPXXX, user.getFixedQtyXRPZ18(), leverage);
//
//        throw new RuntimeException("Fixed qty user calculation failed");
//    }

    private String calculateFixedQtyForSymbol(User user, String symbol, String leverage) {
        if (symbol.equals(Symbol.XBTUSD.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyXBTUSD(), leverage);
        if (symbol.equals(Symbol.ETHUSD.getValue()))
            return calculateOrderQtyETHUSD(user, user.getFixedQtyETHUSD(), leverage);
        if (symbol.equals(Symbol.ADAXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyADAZ18(), leverage);
        if (symbol.equals(Symbol.BCHXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyBCHZ18(), leverage);
        if (symbol.equals(Symbol.EOSXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyEOSZ18(), leverage);
        if (symbol.equals(Symbol.ETHXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyXBTJPY(), leverage);
        //TODO fix these ethh19
        if (symbol.equals(Symbol.LTCXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyLTCZ18(), leverage);
        if (symbol.equals(Symbol.TRXXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyTRXZ18(), leverage);
        if (symbol.equals(Symbol.XRPXXX.getValue()))
            return calculateOrderQty(user, Symbol.valueOf(symbol), user.getFixedQtyXRPZ18(), leverage);

        throw new RuntimeException("Fixed qty user calculation failed");
    }

    private String calculateOrderQty(User user, Symbol symbol, double fixedQty, String leverage) {
        return String.valueOf(Math.round(
                (fixedQty / 100 * Double.parseDouble(get_User_Margin(user).get("walletBalance").toString()) / 100000000) *
                        Double.parseDouble(leverage) *
                        Double.parseDouble(getInstrumentLastPrice(user, symbol))
        ));
    }

    private String calculateOrderQtyETHUSD(User user, double fixedQty, String leverage) {
        return String.valueOf(Math.round(
                ((fixedQty / 100 * Double.parseDouble(get_User_Margin(user).get("walletBalance").toString()) / 100000000) * Double.parseDouble(leverage))
                        / (Double.parseDouble(getInstrumentLastPrice(user, Symbol.ETHUSD)) * 0.000001)
        ));
    }

    private Map<String, Object> getMap(String responseBody) {
        if(responseBody != null) {
            JSONObject jsonObj = new JSONObject(responseBody);
            return new HashMap<>(jsonObj.toMap());
        }
        return Collections.emptyMap();
    }

    private List<Map<String, Object>> getMapList(String responseBody) {
        List<Map<String, Object>> myMapList = new ArrayList<>();
        if(responseBody != null) {
            JSONArray jsonArray = new JSONArray(responseBody);
            myMapList = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                myMapList.add(jsonObj.toMap());
            }
        }
        return myMapList;
    }

    private String calculateSignature(String apiSecret, String verb, String path, String expires, String data)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, Exception {
        Preconditions.checkNotNull(apiSecret, "API Secret");
        Preconditions.checkNotNull(verb, "request method");
        Preconditions.checkNotNull(path, "bitmex path");
        Preconditions.checkNotNull(expires, "expire seconds");
        Preconditions.checkNotNull(data, "request data");

        String message = verb + path + expires + data;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

//        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));

        return Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }

}
