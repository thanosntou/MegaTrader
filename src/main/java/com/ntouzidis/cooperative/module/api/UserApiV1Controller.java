package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiV1Controller {

    private final UserService userService;
    private final BitmexService bitmexService;

    public UserApiV1Controller(UserService userService, BitmexService bitmexService) {
        this.userService = userService;
        this.bitmexService = bitmexService;
    }

    @GetMapping
    public ResponseEntity<?> read()
    {
        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        return ResponseEntity.ok(user);
    }

    @GetMapping("/tx")
    public ResponseEntity<?> getTX(@RequestParam(name = "userId", required = false) Integer id)
    {
        User user;
        if (id == null)
            user = userService.findByUsername("athan")
                    .orElseThrow(() -> new NotFoundException("user not found"));
        else
            user = userService.findById(id)
                    .orElseThrow(() -> new NotFoundException("user not found"));

        List<Map<String, Object>> mapList= bitmexService.get_Order_Order(user);

        return ResponseEntity.ok(mapList);
    }

    @GetMapping("/keys")
    public ResponseEntity<?> updateKeys(@RequestParam(name = "apiKey", required = false) String apiKey,
                                        @RequestParam(name = "apiSecret", required = false) String apiSecret)
    {
        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        userService.saveKeys(user, apiKey, apiSecret);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/keys")
    public ResponseEntity<?> updateKeys2(@RequestParam(name = "apiKey", required = false) String apiKey,
                                        @RequestParam(name = "apiSecret", required = false) String apiSecret)
    {
        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        userService.saveKeys(user, apiKey, apiSecret);

        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/fixedQty")
    public ResponseEntity<?> setFixedQty(@RequestParam(name="fixedQty", required=false) Long qty,
                                         @RequestParam(name="symbol", required=false) String symbol) {

        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (qty != null)
            userService.setFixedQty(user, symbol, qty);

        return ResponseEntity.ok(user);
    }
}
