package com.ntouzidis.cooperative.module.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

@Service
public class RestTemplateService {

  private Logger logger = LoggerFactory.getLogger(RestTemplateService.class);

  private static final long EXPIRES_SECONDS = 1600883067;

  @Value("${baseUrl}")
  private String base_url;

  private final RestTemplate restTemplate;
  private final SimpleEncryptor simpleEncryptor;

  public RestTemplateService(RestTemplate restTemplate, SimpleEncryptor simpleEncryptor) {
    this.restTemplate = restTemplate;
    this.simpleEncryptor = simpleEncryptor;
  }

  public Optional<HttpEntity<String>> GET(User user, String path, String data) {
    Preconditions.checkState(user.getClient() != null, "BitmexUser has not set a client");

    final Optional<String> signatureOpt = calculateSignature(
            simpleEncryptor.decrypt(user.getApiSecret()), HttpMethod.GET.name(), path, data);

    if (signatureOpt.isPresent()) {
      try {
        ResponseEntity<String> res = restTemplate.exchange(
                user.getClient().getValue() + path,
                HttpMethod.GET,
                new HttpEntity<>(getHeaders(simpleEncryptor.decrypt(user.getApiKey()), signatureOpt.get())),
                String.class
        );
        return Optional.of(res);

      } catch (HttpClientErrorException e) {
        String errorMessage = "GET request for user: " + user.getUsername() + " for path: " + path + " failed !!!";
        logger.error(errorMessage);
      }
    }
    return Optional.empty();
  }

  public Optional<HttpEntity<String>> POST(User user, String path, String data) {
    Preconditions.checkState(user.getClient() != null, "BitmexUser has not set a client");

    final Optional<String> signatureOpt = calculateSignature(
            simpleEncryptor.decrypt(user.getApiSecret()), HttpMethod.POST.name(), path, data);

    if (signatureOpt.isPresent()) {
      try {
        ResponseEntity<String> res = restTemplate.exchange(
                user.getClient().getValue() + path,
                HttpMethod.POST,
                new HttpEntity<>(data, getHeaders(simpleEncryptor.decrypt(user.getApiKey()), signatureOpt.get())),
                String.class
        );
        return Optional.of(res);

      } catch (HttpClientErrorException e) {
        String errorMessage = "POST request for user: " + user.getUsername() + " for path: " + path + " failed !!!";
        logger.error(errorMessage);
      }
    }
    return Optional.empty();
  }

  public Optional<HttpEntity<String>> DELETE(User user, String path, String data) {
      Preconditions.checkState(user.getClient() != null, "BitmexUser has not set a client");

      final Optional<String> signatureOpt = calculateSignature(
              simpleEncryptor.decrypt(user.getApiSecret()), HttpMethod.DELETE.name(), path, data);

      if (signatureOpt.isPresent()) {
        try {
          ResponseEntity<String> res = restTemplate.exchange(
                  user.getClient().getValue() + path,
                  HttpMethod.DELETE,
                  new HttpEntity<>(data, getHeaders(simpleEncryptor.decrypt(user.getApiKey()), signatureOpt.get())),
                  String.class
          );
          return Optional.of(res);

        } catch (HttpClientErrorException e) {
          String errorMessage = "DELETE request for user: " + user.getUsername() + " for path: " + path + " failed !!!";
          logger.error(errorMessage);
        }
      }
      return Optional.empty();
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

  private Optional<String> calculateSignature(String apiSecret, String verb, String path, String data) {
    try {
      Preconditions.checkNotNull(apiSecret, "Signature CalculationAPI: Secret is null");
      Preconditions.checkNotNull(verb, "Signature CalculationAPI: Request method is null");
      Preconditions.checkNotNull(path, "Signature CalculationAPI: Path is null");
      Preconditions.checkNotNull(data, "Signature CalculationAPI: Data is null");

      String message = verb + path + EXPIRES_SECONDS + data;

      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
      sha256_HMAC.init(secret_key);

      return Optional.of(Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8))));
    } catch (Exception e) {
      logger.error("Calculation of signature failed");
    }
    return Optional.empty();
  }
}
