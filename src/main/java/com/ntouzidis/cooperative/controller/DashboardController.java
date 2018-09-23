package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.admin.AdminService;
import com.ntouzidis.cooperative.module.customer.CustomerService;
import com.ntouzidis.cooperative.module.member.MemberService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Collections;
import java.util.Formatter;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = {"", "/"})
    public String getDashboard(Model model, Principal principal) {
        Object user = null;

        if (principal != null) {
            String username = principal.getName();
            user = adminService.getByUsername(username);
            if (user == null) user = memberService.getByUsername(username);
            if (user == null) user = customerService.getOne(username);
        }

        if (user != null) model.addAttribute("user", user);

        String res = requestUserDetails();

        model.addAttribute("activeTraders", memberService.getAllSortedAndOrdered("username", "asc"));
        model.addAttribute("availableMargin", res.substring(712, 718));

        return "dashboard";
    }

    private String requestUserDetails() {
        String apikey = "iTBM9g7AHRZz0emT0avsjnLn";
        String apiSecret = "zN_zQHoyxZxnSdPrPCeJUFstZMKuIVA8PWBMsHvkTamXMLD6";
        String expires = String.valueOf(1600883067);
        String verb = "GET";
        String path = "/api/v1/user/margin";
        String data = "";

        String signature = null;

        try {
            signature = calculateSignature(apiSecret, verb, path, expires, data);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("api-expires", expires);
        headers.set("api-key", apikey);
        headers.set("api-signature", signature);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<?> res = restTemplate.exchange("https://www.bitmex.com" + path, HttpMethod.GET, entity, String.class);

        return res.getBody().toString();

    }

    private String calculateSignature(String apiSecret, String verb, String path, String expires, String data) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String message = verb + path + expires + data;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        String resu1 = Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes("UTF-8")));

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes("UTF-8")));

        return resu1;
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }



}
