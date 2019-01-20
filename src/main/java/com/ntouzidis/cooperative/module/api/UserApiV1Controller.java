package com.ntouzidis.cooperative.module.api;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> read(@RequestParam(name = "id", required = false) Integer id,
                                  @RequestParam(name = "name", required = false) String name,
                                  Authentication authentication) {

        Preconditions.checkArgument(id != null || name != null);

        User user;
        if (id != null)
            user = userService.findById(id)
                    .orElseThrow(() -> new NotFoundException("user not found"));
        else
            user = userService.findByUsername("athan")
                    .orElseThrow(() -> new NotFoundException("user not found"));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<User> create(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "email") String email,
                                       @RequestParam(value = "pass") String pass,
                                       @RequestParam(value = "confirmPass") String confirmPass) {

//        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

//        Preconditions.checkArgument(pin != null, "You need a secret pin to create a user. Ask your trader");
        Preconditions.checkArgument(!userService.findByUsername(username).isPresent(), "Username exists");
        Preconditions.checkArgument(pass.equals(confirmPass), "Password doesn't match");
        Preconditions.checkArgument(StringUtils.isNotBlank(email) , "Password doesn't match");

        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        user.setEmail(email);

        userService.createCustomer(user, pass);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/tx")
    public ResponseEntity<?> getTX(@RequestParam(name = "userId", required = false) Integer id,
                                   Authentication authentication)
    {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        //TODO implement this
//        User user;
//        if (id == null)
//            user = userService.findByUsername("athan")
//                    .orElseThrow(() -> new NotFoundException("user not found"));
//        else
//            user = userService.findById(id)
//                    .orElseThrow(() -> new NotFoundException("user not found"));

        List<Map<String, Object>> mapList= bitmexService.get_Order_Order(user);

        return ResponseEntity.ok(mapList);
    }

    @GetMapping("/keys")
    public ResponseEntity<?> updateKeys(@RequestParam(name = "apiKey", required = false) String apiKey,
                                        @RequestParam(name = "apiSecret", required = false) String apiSecret,
                                        Authentication authentication)
    {
        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        userService.saveKeys(user, apiKey, apiSecret);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/keys")
    public ResponseEntity<?> updateKeys2(@RequestParam(name = "apiKey", required = false) String apiKey,
                                        @RequestParam(name = "apiSecret", required = false) String apiSecret,
                                         Authentication authentication)
    {
        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        userService.saveKeys(user, apiKey, apiSecret);

        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/fixedQty")
    public ResponseEntity<?> setFixedQty(@RequestParam(name="fixedQty", required=false) Long qty,
                                         @RequestParam(name="symbol", required=false) String symbol,
                                         Authentication authentication)
    {
        User user = userService.findByUsername("athan")
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (qty != null)
            userService.setFixedQty(user, symbol, qty);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<CustomUserDetails> authenticate(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
