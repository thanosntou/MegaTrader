package com.ntouzidis.bitmex_trader.module.trade.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static com.ntouzidis.bitmex_trader.CooperativeApplication.logger;
import static lombok.Lombok.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
@RequiredArgsConstructor
public class RestTemplateService implements IRestTemplateService {

  private static final long EXPIRES_SECONDS = 1706899909;
  private static final String HMAC_SHA_256 = "HmacSHA256";
  private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

  private static final String CLIENT_NOT_SET_MESSAGE = "User has not set a client";
  private static final String SIGNATURE_CALCULATION_FAILED_MESSAGE = "Calculation of signature failed";
  private static final String SIGNATURE_CALCULATION_NULL_PARAM = "Signature CalculationAPI: %s is null";
  private static final String FAILED_REQUEST_MESSAGE = "{} request for user: [{}] for path: [{}] failed";

  private static final String API_KEY_HEADER = "api-key";
  private static final String API_EXPIRES_HEADER = "api-expires";
  private static final String API_SIGNATURE_HEADER = "api-signature";
  private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";

  private final RestTemplate restTemplate;

  @Override
  public Optional<HttpEntity<String>> get(User user, String path, String data) {
    return call(GET, user,  path, data);
  }

  @Override
  public Optional<HttpEntity<String>> post(User user, String path, String data) {
    return call(POST, user,  path, data);
  }

  @Override
  public Optional<HttpEntity<String>> delete(User user, String path, String data) {
    return call(DELETE, user,  path, data);
  }

  private Optional<HttpEntity<String>> call(HttpMethod method, User user, String path, String data) {
    Preconditions.checkState(Objects.nonNull(user.getClient()), CLIENT_NOT_SET_MESSAGE);

    String url = user.getClient().getValue() + path;
    String signature = calculateSignature(user, method, path, data);
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(getHeaders(user.getApiKey(), signature));
    try {
      return Optional.of(restTemplate.exchange(url, method, httpEntity, String.class));
    } catch (HttpClientErrorException e) {
      logger.error(FAILED_REQUEST_MESSAGE, method.name(), user.getUsername(), path, e);
      return Optional.empty();
    }
  }

  private HttpHeaders getHeaders(String apiKey, String signature) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(APPLICATION_JSON));
    headers.setContentType(APPLICATION_FORM_URLENCODED);
    headers.set(X_REQUESTED_WITH_HEADER, XML_HTTP_REQUEST);
    headers.set(API_EXPIRES_HEADER, String.valueOf(EXPIRES_SECONDS));
    headers.set(API_KEY_HEADER, apiKey);
    headers.set(API_SIGNATURE_HEADER, signature);
    return headers;
  }

  private String calculateSignature(User user, HttpMethod method, String path, String data) {
    checkNotNull(user, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "User"));
    checkNotNull(method, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "Method"));
    checkNotNull(path, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "Path"));
    checkNotNull(data, String.format(SIGNATURE_CALCULATION_NULL_PARAM, "Data"));

    String apiSecret = user.getApiSecret();

    if (isBlank(apiSecret))
      throw new RuntimeException("Bitmex API secret is not set");

    String message = method.name() + path + EXPIRES_SECONDS + data;
    try {
      Mac sha256HMAC = Mac.getInstance(HMAC_SHA_256);
      SecretKeySpec secretKey = new SecretKeySpec(apiSecret.getBytes(), HMAC_SHA_256);
      sha256HMAC.init(secretKey);
      return Hex.encodeHexString(sha256HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
      throw new RuntimeException(SIGNATURE_CALCULATION_FAILED_MESSAGE, e);
    }
  }
}
