package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.enumeration.Client;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.ID_PATH;
import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.USER_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.*;
import static com.ntouzidis.cooperative.module.common.RolesConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    value = USER_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class UserApiV1Controller {

  private Logger logger = LoggerFactory.getLogger(UserApiV1Controller.class);

  private static final String ALL_PATH = "/all";
  private static final String AUTHENTICATE_PATH = "/authenticate";
  private static final String CLIENT_PATH = "/client";
  private static final String FIXED_QTY_PATH = "/fixedQty";
  private static final String NEW_PATH = "/new";
  private static final String KEYS_PATH = "/keys";
  private static final String PASS_PATH = "/pass";

  private final Context context;
  private final UserService userService;
  private final SimpleEncryptor simpleEncryptor;

  public UserApiV1Controller(UserService userService,
                             SimpleEncryptor simpleEncryptor,
                             Context context) {
    this.userService = userService;
    this.simpleEncryptor = simpleEncryptor;
    this.context = context;
  }

  @GetMapping
  @PreAuthorize(AUTHENTICATED)
  public ResponseEntity<User> read(
      @RequestParam(name = ID_PARAM, required = false) Integer id,
      @RequestParam(name = USERNAME_PARAM, required = false) String username
  ) {
    User user = userService.getOne(id, username);

    if (userService.isAdmin(context.getUser()))
      decryptApiKeys(user);

    return ResponseEntity.ok(user);
  }

  @GetMapping(ALL_PATH)
  @PreAuthorize(ADMIN_ROLE)
  public ResponseEntity<List<User>> readAll() {
    List<User> users = userService.getAll();
    users.forEach(this::decryptApiKeys);
    return ResponseEntity.ok(users);
  }

  @PostMapping(NEW_PATH)
  @PreAuthorize(TRADER_ROLE)
  public ResponseEntity<User> create(
      @RequestParam(USERNAME_PARAM) String username,
      @RequestParam(EMAIL_PARAM) String email,
      @RequestParam(PASS_PARAM) String pass,
      @RequestParam(CONFIRM_PASS_PARAM) String confirmPass,
      @RequestParam(PIN_PARAM) String PIN
  ) {
    checkArgument(pass.equals(confirmPass), "Password doesn't match");
    checkArgument(StringUtils.isNotBlank(email) , "Wrong email");

    LocalDate today = LocalDate.now();
    String dailyPIN = String.valueOf(today.getDayOfMonth() + today.getMonthValue() + today.getYear());
    checkArgument(dailyPIN.equals(PIN), "WRONG PIN");

    User user = userService.createCustomer(username, email, pass);
    // for the ui, to login immediately after creation
    user.setPassword(confirmPass);
    return ResponseEntity.ok(user);
  }

  @PostMapping(KEYS_PATH)
  public ResponseEntity<User> updateKeys(
      @RequestParam(name = API_KEY_PARAM, required = false) String apiKey,
      @RequestParam(name = API_SECRET_PARAM, required = false) String apiSecret
  ) {
    return ResponseEntity.ok(userService.saveKeys(userService.getOne(context.getUserID()), apiKey, apiSecret));
  }

  @PostMapping(CLIENT_PATH)
  public ResponseEntity<User> updateClient(
      @RequestParam(name = CLIENT_PARAM, required = false) Client client
  ) {
    return ResponseEntity.ok(userService.updateClient(userService.getOne(context.getUserID()), client));
  }

  @PostMapping(FIXED_QTY_PATH)
  public ResponseEntity<User> setFixedQty(
      @RequestParam(SYMBOL_PARAM) String symbol,
      @RequestParam(FIXED_QTY_PARAM) long qty
  ) {
    return ResponseEntity.ok(userService.setFixedQty(userService.getOne(context.getUserID()), symbol, qty));
  }

  @PostMapping(PASS_PATH)
  public ResponseEntity<User> updatePass(
      @RequestParam(OLD_PASS_PARAM) String oldPass,
      @RequestParam(NEW_PASS_PARAM) String newPass,
      @RequestParam(CONFIRM_PASS_PARAM) String confirmPass
  ) {
    return ResponseEntity.ok(userService.changePassword(context.getUserID(), newPass, confirmPass));
  }

  @DeleteMapping(ID_PATH)
  public ResponseEntity<User> delete(
      @PathVariable Integer id
  ) {
      checkArgument(userService.isAdmin(context.getUser()));
      User user = userService.getOne(id);
      userService.delete(user);
      return ResponseEntity.ok(user);
  }

  @GetMapping(AUTHENTICATE_PATH)
  @PreAuthorize(AUTHENTICATED)
  public ResponseEntity<CustomUserDetails> authenticate() {
        return ResponseEntity.ok(context.getCustomUserDetails());
  }

  private void decryptApiKeys(User user) {
    try {
      user.setApiKey(simpleEncryptor.decrypt(user.getApiKey()));
      user.setApiSecret(simpleEncryptor.decrypt(user.getApiSecret()));
    } catch (Exception e) {
      logger.warn(String.format("Api keys decryption %s failed", e.getMessage()));
    }
  }
}
