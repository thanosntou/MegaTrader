package com.ntouzidis.cooperative.module.api_v1;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.enumeration.Client;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
  private final SimpleEncryptor simpleEncryptor;

  public UserApiV1Controller(UserService userService,
                             SimpleEncryptor simpleEncryptor) {
    this.userService = userService;
    this.simpleEncryptor = simpleEncryptor;
  }

  @GetMapping(
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> read(Authentication authentication,
                                @RequestParam(name = "id", required = false) Integer id,
                                @RequestParam(name = "name", required = false) String name)
  {
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

    return ResponseEntity.ok(user);
  }

  @GetMapping(
          value = "all",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> readAll(Authentication authentication)
  {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    Preconditions.checkArgument(userService.isAdmin(userDetails.getUser()));

    List<User> users = userService.findAll();

    if (userService.isAdmin(userDetails.getUser()))
      users = users.stream().peek(this::decryptApiKeys).collect(Collectors.toList());

    return ResponseEntity.ok(users);
  }

  @PostMapping(
          value = "/new",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<User> create(
          @RequestParam(value = "username") String username,
          @RequestParam(value = "email") String email,
          @RequestParam(value = "pass") String pass,
          @RequestParam(value = "confirmPass") String confirmPass,
          @RequestParam(value = "PIN") String PIN
  ) {
    Preconditions.checkArgument(!userService.findByUsername(username).isPresent(), "Username exists");
    Preconditions.checkArgument(pass.equals(confirmPass), "Password doesn't match");
    Preconditions.checkArgument(StringUtils.isNotBlank(email) , "Password doesn't match");

    LocalDate today = LocalDate.now();
    String dailyPIN = String.valueOf(today.getDayOfMonth() + today.getMonthValue() + today.getYear());
    Preconditions.checkArgument(dailyPIN.equals(PIN), "WRONG PIN");

    User user = userService.createCustomer(username, email, pass, confirmPass);
    // for the ui, to login immediately after creation
    user.setPassword(confirmPass);

    return ResponseEntity.ok(user);
  }



  @PostMapping(
          value = "/keys",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> updateKeys(Authentication authentication,
                                        @RequestParam(name = "apiKey", required = false) String apiKey,
                                        @RequestParam(name = "apiSecret", required = false) String apiSecret)
  {
    CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());
    User user = userService.getOne(userDetails.getUser().getId());
    return ResponseEntity.ok(userService.saveKeys(user, apiKey, apiSecret));
  }

  @PostMapping(
          value = "/client",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> updateClient(Authentication authentication,
                                          @RequestParam(name = "client", required = false) Client client)
  {
    CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

    return ResponseEntity.ok(userService.updateClient(userService.getOne(userDetails.getUser().getId()), client));
  }

  @PostMapping(
          value = "/fixedQty",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> setFixedQty(Authentication authentication,
                                         @RequestParam("symbol") String symbol,
                                         @RequestParam("fixedQty") long qty)
  {
    CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());

    User user = userService.getOne(userDetails.getUser().getId());

    return ResponseEntity.ok(userService.setFixedQty(user, symbol, qty));
  }

  @PostMapping(
            value = "/pass",
            produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<User> updatePass(Authentication authentication,
                                           @RequestParam("oldPass") String oldPass,
                                           @RequestParam("newPass") String newPass,
                                           @RequestParam("confirmPass") String confirmPass)
  {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    return ResponseEntity.ok(userService.changePassword(userDetails.getUser().getId(), newPass, confirmPass));
  }


  @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> delete(Authentication authentication,
                                    @PathVariable Integer id)
  {
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      Preconditions.checkArgument(userService.isAdmin(userDetails.getUser()));

      User user = userService.getOne(id);
      userService.delete(user);

      return ResponseEntity.ok(user);
  }

  @GetMapping(
            value = "/authenticate",
            produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<CustomUserDetails> authenticate(Authentication authentication) {
        return ResponseEntity.ok((CustomUserDetails) authentication.getPrincipal());
  }

  private void decryptApiKeys(User user) {
    try {
      user.setApiKey(simpleEncryptor.decrypt(user.getApiKey()));
      user.setApiSecret(simpleEncryptor.decrypt(user.getApiSecret()));
    } catch (Exception e) {
      logger.warn("Api keys decryption failed", e.getMessage());
    }
  }
}
