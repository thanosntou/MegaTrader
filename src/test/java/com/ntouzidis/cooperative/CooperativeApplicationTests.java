package com.ntouzidis.cooperative;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CooperativeApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void calculateSignature() throws NoSuchAlgorithmException, InvalidKeyException {
        String apiSecret = "U77pfEEIlxL-4jugqU-_FVAFWiOMANA-G3YWbzwNuidOl0Ho";
        String verb = "GET";
        String path = "/realtime";
        String expires = String.valueOf(1600883067);
        String data = "subscribe=order,orderBook:XBTUSD";

        String message = verb + path + expires;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        System.out.println(Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8))));
    }

}
