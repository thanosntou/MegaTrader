package com.ntouzidis.cooperative.module.api_v1;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.common.enumeration.Client;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiV1Controller {

    Logger logger = LoggerFactory.getLogger(UserApiV1Controller.class);

    @Value("${trader}")
    private String traderName;

    @Value("${superAdmin}")
    private String superAdmin;

    private final UserService userService;
    private final TradeService tradeService;
    private final BitmexService bitmexService;
    private final SimpleEncryptor simpleEncryptor;

    public UserApiV1Controller(
            UserService userService,
            TradeService tradeService,
            BitmexService bitmexService,
            SimpleEncryptor simpleEncryptor
    ) {
        this.userService = userService;
        this.tradeService = tradeService;
        this.bitmexService = bitmexService;
        this.simpleEncryptor = simpleEncryptor;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> read(@RequestParam(name = "id", required = false) Integer id,
                                  @RequestParam(name = "name", required = false) String name,
                                  Authentication authentication
    ) {
        Preconditions.checkArgument(id != null || name != null);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Optional<User> userOpt;
        if (id != null)
            userOpt = userService.findById(id);
        else
            userOpt = userService.findByUsername(name);

        User user = userOpt.orElseThrow(() -> new NotFoundException("user not found"));

        if (userService.isAdmin(userDetails.getUser()))
            decryptApiKeys(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(
            value = "all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> readAll(Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Preconditions.checkArgument(userService.isAdmin(userDetails.getUser()));

        List<User> users = userService.findAll();

        if (userService.isAdmin(userDetails.getUser()))
            users = users.stream().peek(this::decryptApiKeys).collect(Collectors.toList());

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
                new RuntimeException("BitmexUser don't have a personal trader"));

        return ResponseEntity.ok(personalTrader);
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

        return ResponseEntity.ok(trader);
    }

    @PostMapping(
            value = "/unfollow",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> unfollowTrader(Authentication authentication
    ) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        userService.unlinkTrader(user);

        return ResponseEntity.ok(user);
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

        User user = userService.createCustomer(username, email, pass, confirmPass);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/wallet",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Object>> getUserBitmexWallet(
            @RequestParam("id") int id,
            Authentication authentication
    ) {
        User userDetails = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(
                userService.isAdmin(userDetails) || userService.isTrader(userDetails)
        );
        User user = userService.getOne(id);

        Map<String, Object> wallet = bitmexService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @GetMapping(
            value = "/wallet/history",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletHistory(
            @RequestParam("id") int id,
            Authentication authentication
    ) {
        User userDetails = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(
                userService.isAdmin(userDetails) || userService.isTrader(userDetails)
        );
        User user = userService.getOne(id);

        List<Map<String, Object>> wallet = bitmexService.getUserWalletHistory(user)
                .stream().limit(75).collect(Collectors.toList());

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @GetMapping(
            value = "/wallet/summary",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Map<String, Object>>> getUserBitmexWalletSummary(
            @RequestParam("id") int id,
            Authentication authentication
    ) {
        User userDetails = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(
                userService.isAdmin(userDetails) || userService.isTrader(userDetails)
        );
        User user = userService.getOne(id);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("Hello World"));

        List<Map<String, Object>> wallet = bitmexService.getUserWalletSummary(user)
                .stream().limit(50).collect(Collectors.toList());

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @GetMapping(
            value = "/active_orders",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Map<String, Object>>> getActiveOrders(@RequestParam("id") int id,
                                                                     Authentication authentication
    ) {
        User userDetails = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(
                userService.isAdmin(userDetails) || userService.isTrader(userDetails)
        );
        User user = userService.getOne(id);

        List<Map<String, Object>> randomActiveOrders = tradeService.getRandomActiveOrdersOf(user);

        return new ResponseEntity<>(randomActiveOrders, HttpStatus.OK);
    }

    @GetMapping("/active_positions")
    public ResponseEntity<List<Map<String, Object>>> getOpenPositions(@RequestParam("id") int id,
                                                                      Authentication authentication
    ) {
        User userDetails = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        Preconditions.checkArgument(
                userService.isAdmin(userDetails) || userService.isTrader(userDetails)
        );
        User user = userService.getOne(id);

        List<Map<String, Object>> randomOpenPositions = tradeService.getRandomPositionsOf(user);

        return new ResponseEntity<>(randomOpenPositions, HttpStatus.OK);
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
    public ResponseEntity<?> updateKeys(
            @RequestParam(name = "apiKey", required = false) String apiKey,
            @RequestParam(name = "apiSecret", required = false) String apiSecret,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

        User user = userService.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new IllegalStateException("BitmexUser not found"));

        return ResponseEntity.ok(userService.saveKeys(user, apiKey, apiSecret));
    }

    @PostMapping(
            value = "/client",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateClient(
            @RequestParam(name = "client", required = false) Client client,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

        User user = userService.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new IllegalStateException("BitmexUser not found"));

        return ResponseEntity.ok(userService.updateClient(user, client));
    }

    @PostMapping(
            value = "/fixedQty",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> setFixedQty(@RequestParam("symbol") String symbol,
                                         @RequestParam("fixedQty") long qty,
                                         Authentication authentication
    ) {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

        User user = userService.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new IllegalStateException("BitmexUser not found"));

        return new ResponseEntity<>(userService.setFixedQty(user, symbol, qty), HttpStatus.OK);
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

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Preconditions.checkArgument(userService.isAdmin(userDetails.getUser()));

        User user = userService.getOne(id);

        userService.delete(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
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

    private void decryptApiKeys(User user) {
        try {
            user.setApiKey(simpleEncryptor.decrypt(user.getApiKey()));
            user.setApiSecret(simpleEncryptor.decrypt(user.getApiSecret()));
        } catch (Exception e) {
            logger.warn("Api keys decryption failed", e);
        }

    }
}