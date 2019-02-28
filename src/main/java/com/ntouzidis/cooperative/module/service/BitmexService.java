package com.ntouzidis.cooperative.module.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataPostLeverage;
import com.ntouzidis.cooperative.module.common.builder.DataPostOrderBuilder;
import com.ntouzidis.cooperative.module.common.endpoints.*;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BitmexService {

    private Logger logger = LoggerFactory.getLogger(BitmexService.class);

    private static String ENDPOINT_POSITION = "/api/v1/position";
    private static String ENDPOINT_POSITION_LEVERAGE = "/api/v1/position/leverage";

    private static final long EXPIRES_SECONDS = 1600883067;

    @Value("${baseUrl}")
    private String base_url;

    @Autowired
    private RestTemplate restTemplate;
    private final SimpleEncryptor simpleEncryptor;

    public BitmexService(SimpleEncryptor simpleEncryptor) {
        this.simpleEncryptor = simpleEncryptor;
    }

    public List<Map<String, Object>> get_Announcements(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, Announcement.ANNOUNCEMENT, "")
                .orElseThrow(() -> new RuntimeException("Call to " + Announcement.ANNOUNCEMENT + " failed.")));
    }

    public List<Map<String, Object>> get_chat(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, Chat.CHAT + "?reverse=true&count=200", "")
                .orElseThrow(() -> new RuntimeException("Call to " + Chat.CHAT + " failed.")));
    }

    public Map<String, Object> post_chat(User user, String message) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMap(requestPOST(user, Chat.CHAT, "message="+ message)
                .orElseThrow(() -> new RuntimeException("Call to " + Chat.CHAT + " failed.")));
    }

    public String getInstrumentLastPrice(User user, Symbol symbol) {
        Preconditions.checkNotNull(user, "user cannot be null");
        Preconditions.checkNotNull(symbol, "symbol cannot be null");

        List<Map<String, Object>> maplist = getMapList(
                requestGET(user,Instrument.INSTRUMENT + "?symbol=" + symbol.getValue(), "")
                        .orElseThrow(() -> new RuntimeException("Call to " + Instrument.INSTRUMENT + " failed."))
        );
        Preconditions.checkState(symbol.getValue().equals(maplist.get(0).get("symbol")));
        return maplist.get(0).get("lastPrice").toString();
    }

    public Map<String, Object> getUserWallet(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMap(requestGET(user, BitmexUser.USER_WALLET, "")
                .orElseThrow(() -> new RuntimeException("Call to " + BitmexUser.USER_WALLET + " failed.")));
    }

    public List<Map<String, Object>> getUserWalletHistory(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, BitmexUser.USER_WALLET_HISTORY, "")
                .orElseThrow(() -> new RuntimeException("Call to " + BitmexUser.USER_WALLET_HISTORY + " failed.")));
    }

    public List<Map<String, Object>> getUserWalletSummary(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, BitmexUser.USER_WALLET_SUMMARY, "")
                .orElseThrow(() -> new RuntimeException("Call to " + BitmexUser.USER_WALLET_SUMMARY + " failed.")));
    }

    public Map<String, Object> get_User_Margin(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMap(requestGET(user, BitmexUser.USER_MARGIN, "")
                .orElseThrow(() -> new RuntimeException("Call to " + BitmexUser.USER_MARGIN + " failed.")));
    }

    public List<Map<String, Object>> get_Order_Order(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, Order.ORDER + "?reverse=true", "")
                .orElseThrow(() -> new RuntimeException("Call to " + Order.ORDER + " failed.")));
    }

    public Map<String, Object> post_Order_Order(User user, DataPostOrderBuilder dataOrder) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMap(requestPOST(user, Order.ORDER, dataOrder.get())
                .orElseThrow(() -> new RuntimeException("Call to " + Order.ORDER + " failed.")));
    }

    public void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrder) {
        requestDELETE(user, Order.ORDER, dataDeleteOrder.get());
    }

    public void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrderBuilder) {
        requestDELETE(user, Order.ORDER_ALL, dataDeleteOrderBuilder.get());
    }

    public Map<String, Object> getSymbolPosition(User user, Symbol symbol) {
        Preconditions.checkNotNull(user, "user cannot be null");

        Optional<String> res = requestGET(user, ENDPOINT_POSITION, "");

        return getMapList(res.orElse(null))
                .stream()
                .filter(i -> i.get("symbol").equals(symbol.getValue()))
                .findAny().orElse(null);
    }

    public List<Map<String, Object>> getAllSymbolPosition(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, ENDPOINT_POSITION, "")
                .orElseThrow(() -> new RuntimeException("Call to " + ENDPOINT_POSITION + " failed.")));
    }

    public List<Map<String, Object>> get_Position(User user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, ENDPOINT_POSITION, "")
                .orElseThrow(() -> new RuntimeException("Call to " + ENDPOINT_POSITION + " failed.")));
    }

    public List<Map<String, Object>> get_Position_Leverage(User user, String data) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMapList(requestGET(user, ENDPOINT_POSITION_LEVERAGE, data)
                .orElseThrow(() -> new RuntimeException("Call to " + ENDPOINT_POSITION_LEVERAGE + " failed.")));
    }

    public Map<String, Object> post_Position_Leverage(User user, DataPostLeverage dataLeverageBuilder) {
        Preconditions.checkNotNull(user, "user cannot be null");

        return getMap(requestPOST(user, ENDPOINT_POSITION_LEVERAGE, dataLeverageBuilder.get())
                .orElseThrow(() -> new RuntimeException("Call to " + ENDPOINT_POSITION_LEVERAGE + " failed.")));
    }

    private Optional<String> requestGET(User user, String path, String data) {
        String apikey, apiSecret, signature;

        try {
            Preconditions.checkState(user.getClient() != null, "BitmexUser has not set a client");

            apikey = simpleEncryptor.decrypt(user.getApiKey());
            apiSecret = simpleEncryptor.decrypt(user.getApiSecret());
            signature = calculateSignature(apiSecret, HttpMethod.GET.name(), path, data);

            ResponseEntity<String> res = restTemplate.exchange(
                    user.getClient().getValue() + path,
                    HttpMethod.GET,
                    new HttpEntity<>(getHeaders(apikey, signature)),
                    String.class
            );
            return Optional.of(Objects.requireNonNull(res.getBody()));

        } catch (Exception e) {
            logger.error("Rest call failed", e);
        }
        return Optional.empty();
    }

    private Optional<String> requestPOST(User user, String path, String data) {
        String apikey, apiSecret, signature;

        try {
            Preconditions.checkState(user.getClient() != null, "BitmexUser has not set a client");

            apikey = simpleEncryptor.decrypt(user.getApiKey());
            apiSecret = simpleEncryptor.decrypt(user.getApiSecret());
            signature = calculateSignature(apiSecret, HttpMethod.POST.name(), path, data);

            ResponseEntity<String> res = restTemplate.exchange(
                    user.getClient().getValue() + path,
                    HttpMethod.POST,
                    new HttpEntity<>(data, getHeaders(apikey, signature)),
                    String.class
            );
            return Optional.of(Objects.requireNonNull(res.getBody()));

        } catch (Exception e) {
            logger.error("Rest call failed", e);
        }
        return Optional.empty();
    }

    private void requestDELETE(User user, String path, String data) {
        String apikey, apiSecret, signature;

        try {
            Preconditions.checkState(user.getClient() != null, "BitmexUser has not set a client");

            apikey = simpleEncryptor.decrypt(user.getApiKey());
            apiSecret = simpleEncryptor.decrypt(user.getApiSecret());

            signature = calculateSignature(apiSecret, HttpMethod.DELETE.name(), path, data);

            ResponseEntity<String> res = restTemplate.exchange(
                    user.getClient().getValue() + path,
                    HttpMethod.DELETE,
                    new HttpEntity<>(data, getHeaders(apikey, signature)),
                    String.class
            );
            Objects.requireNonNull(res.getBody());

        } catch (Exception e) {
            logger.error("Rest call failed", e);
        }
    }

//    private String requestGET3(String username, String baseUrl, String path, String data) {
//        String apikey = principal.getApiKey();
//        String apiSecret = principal.getApiSecret();
//        try {
//            ResponseEntity<String> response
//                    = restTemplate.getForEntity(fooResourceUrl + "/1", String.class, builder);
//
//            String signature = calculateSignature(apiSecret, verb, path, expires, data);
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
//        } catch (NoSuchAlgorithmException | InvalidKeyException | HttpClientErrorException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

    private HttpHeaders getHeaders(String apiKey, String signature) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("api-expires", String.valueOf(EXPIRES_SECONDS));
        headers.set("api-key", apiKey);
        headers.set("api-signature", signature);
        return headers;
    }

    private String calculateSignature(String apiSecret, String verb, String path, String data) {
        try {
            Preconditions.checkNotNull(apiSecret, "API Secret");
            Preconditions.checkNotNull(verb, "request method");
            Preconditions.checkNotNull(path, "bitmex path");
            Preconditions.checkNotNull(data, "request data");

            String message = verb + path + EXPIRES_SECONDS + data;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            logger.error("Signature calculation failed", e);
        }
        throw new RuntimeException("Signature calculation failed");
    }
}
