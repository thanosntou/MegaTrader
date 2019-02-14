package com.ntouzidis.cooperative.module.api;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.bitmex.BitmexService;
import com.ntouzidis.cooperative.module.common.enumeration.Client;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiV1Controller {

    @Value("${trader}")
    private String traderName;

    @Value("${superAdmin}")
    private String superAdmin;

    private final UserService userService;
    private final BitmexService bitmexService;
    private final PasswordEncoder passwordEncoder;

    public UserApiV1Controller(UserService userService,
                               BitmexService bitmexService,
                               PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.bitmexService = bitmexService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> read(@RequestParam(name = "id", required = false) Integer id,
                                  @RequestParam(name = "name", required = false) String name,
                                  Authentication authentication
    ) {
        Preconditions.checkArgument(id != null || name != null);

        Optional<User> userOpt;
        if (id != null)
            userOpt = userService.findById(id);
        else
            userOpt = userService.findByUsername(name);

        User user = userOpt.orElseThrow(() -> new NotFoundException("user not found"));

        return new ResponseEntity<>(encodeUserApiKeys(user), HttpStatus.OK);
    }

    @GetMapping(
            value = "all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> readAll(Authentication authentication
    ) {
        List<User> users = userService.findAll();

        users.forEach(this::encodeUserApiKeys);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(
            value = "/personal",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getPersonalTrader(Authentication authentication
    ) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        User personalTrader = userService.getPersonalTrader(user.getUsername()).orElseThrow(() ->
                new RuntimeException("User don't have a personal trader"));

        return ResponseEntity.ok(encodeUserApiKeys(personalTrader));
    }

    @PostMapping(
            value = "/follow",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> followTrader(@RequestParam(name = "traderId") Integer traderId,
                                             Authentication authentication
    ) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        User trader = userService.findTrader(traderId).orElseThrow(() -> new RuntimeException("Trader not found"));

        userService.linkTrader(user, trader.getId());

        return ResponseEntity.ok(encodeUserApiKeys(trader));
    }

    @PostMapping(
            value = "/unfollow",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> unfollowTrader(Authentication authentication
    ) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        userService.unlinkTrader(user);

        return ResponseEntity.ok(encodeUserApiKeys(user));
    }

    @PostMapping("/new")
    public ResponseEntity<User> create(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "email") String email,
                                       @RequestParam(value = "pass") String pass,
                                       @RequestParam(value = "confirmPass") String confirmPass
    ) {
//        Preconditions.checkArgument(pin != null, "You need a secret pin to create a user. Ask your trader");
        Preconditions.checkArgument(!userService.findByUsername(username).isPresent(), "Username exists");
        Preconditions.checkArgument(pass.equals(confirmPass), "Password doesn't match");
        Preconditions.checkArgument(StringUtils.isNotBlank(email) , "Password doesn't match");

        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        user.setEmail(email);
        user.setApiKey("");
        user.setApiSecret("");
        user.setEnabled(false);
        user.setFixedQtyXBTUSD(0L);
        user.setFixedQtyETHUSD(0L);
        user.setFixedQtyADAZ18(0L);
        user.setFixedQtyBCHZ18(0L);
        user.setFixedQtyEOSZ18(0L);
        user.setFixedQtyXBTJPY(0L);//TODO should change to ethxxx
        user.setFixedQtyLTCZ18(0L);
        user.setFixedQtyTRXZ18(0L);
        user.setFixedQtyXRPZ18(0L);

        userService.createCustomer(user, pass);

        return new ResponseEntity<>(encodeUserApiKeys(user), HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/tx",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getTX(@RequestParam(name = "id", required = false) Integer id,
                                   Authentication authentication
    ) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        if (id != null)
            user = userService.findById(id).orElseThrow(() -> new NotFoundException("user not found"));

        return ResponseEntity.ok(bitmexService.get_Order_Order(user));
    }

    @PostMapping(
            value = "/keys",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateKeys(@RequestParam(name = "apiKey", required = false) String apiKey,
                                        @RequestParam(name = "apiSecret", required = false) String apiSecret,
                                         Authentication authentication
    ) {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

        User user = userService.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new IllegalStateException("User not found"));

        return ResponseEntity.ok(encodeUserApiKeys(userService.saveKeys(user, apiKey, apiSecret)));
    }

    @PostMapping(
            value = "/client",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateClient(@RequestParam(name = "client", required = false) Client client,
                                          Authentication authentication
    ) {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

        User user = userService.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new IllegalStateException("User not found"));

        return ResponseEntity.ok(encodeUserApiKeys(userService.updateClient(user, client)));
    }

    @PostMapping(
            value = "/fixedQty",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> setFixedQty(@RequestParam("symbol") String symbol,
                                         @RequestParam("fixedQty") Long qty,
                                         Authentication authentication
    ) {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

        User user = userService.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new IllegalStateException("User not found"));

        return new ResponseEntity<>(encodeUserApiKeys(userService.setFixedQty(user, symbol, qty)), HttpStatus.OK);
    }

    @PostMapping(
            value = "/pass",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> updatePass(@RequestParam("oldPass") String oldPass,
                                           @RequestParam("newPass") String newPass,
                                           @RequestParam("confirmPass") String confirmPass,
                                           Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userService.changePassword(userDetails.getUser().getId(), newPass, confirmPass);

        return new ResponseEntity<>(encodeUserApiKeys(user), HttpStatus.OK);
    }

    @GetMapping(
            value = "/authenticate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomUserDetails> authenticate(Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    private User encodeUserApiKeys(User user) {
        if (user.getApiKey() != null)
            user.setApiKey(passwordEncoder.encode(user.getApiKey()));
        if (user.getApiSecret() != null)
            user.setApiSecret(passwordEncoder.encode(user.getApiSecret()));

        return user;
    }
}
