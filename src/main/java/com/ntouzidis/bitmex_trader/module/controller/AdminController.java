package com.ntouzidis.bitmex_trader.module.controller;

import com.google.common.collect.ImmutableMap;
import com.ntouzidis.bitmex_trader.module.common.attribute_converters.CryptoConverter;
import com.ntouzidis.bitmex_trader.module.common.dto.LoginDTO;
import com.ntouzidis.bitmex_trader.module.common.dto.UserDTO;
import com.ntouzidis.bitmex_trader.module.common.pojo.Context;
import com.ntouzidis.bitmex_trader.module.common.utils.UserUtils;
import com.ntouzidis.bitmex_trader.module.trade.service.IBitmexTradeService;
import com.ntouzidis.bitmex_trader.module.user.entity.Login;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.repository.LoginRepository;
import com.ntouzidis.bitmex_trader.module.user.service.AdminService;
import com.ntouzidis.bitmex_trader.module.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ntouzidis.bitmex_trader.module.common.constants.AuthorizationConstants.ADMIN;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.ACTIVE_VOLUME;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.TOTAL_VOLUME;
import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.*;
import static com.ntouzidis.bitmex_trader.module.common.constants.ParamConstants.*;
import static com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol.ETHUSD;
import static com.ntouzidis.bitmex_trader.module.common.utils.UserUtils.toDTO;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    value = ADMIN_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
@Api(tags = "Admin API")
@RequiredArgsConstructor
public class AdminController {

  private final Context context;
  private final UserService userService;
  private final AdminService adminService;
  private final IBitmexTradeService traderService;
  private final LoginRepository loginRepository;

  @GetMapping("/users")
  @PreAuthorize(ADMIN)
  @ApiOperation("Read all Users (Admin-only)")
  public ResponseEntity<List<UserDTO>> readAllUsers() {
    return ok((userService.getAll())
            .stream()
            .map(UserUtils::toDTOForAdmin)
            .collect(toList())
    );
  }

  @GetMapping("/traders")
  @PreAuthorize(ADMIN)
  @ApiOperation("Read all Traders (Admin-only)")
  public ResponseEntity<List<User>> readAll() {
    return ok(userService.getTraders());
  }

  @GetMapping("/traders/{traderId}/admins")
  @PreAuthorize(ADMIN)
  @ApiOperation("Read all Traders (Admin-only)")
  public ResponseEntity<List<User>> readAllAdminsOfTrader() {
    return ok(userService.getTraders());
  }

  @GetMapping("/user/{id}")
  @PreAuthorize(ADMIN)
  @ApiOperation("Read a User by ID (Admin-only)")
  public ResponseEntity<UserDTO> readUser(@PathVariable Long id) {
    return ok(Optional.of(userService.getOne(id))
            .map(i -> toDTO(i, false))
            .map(CryptoConverter::encryptApiKeys)
            .orElseThrow()
    );
  }

  @GetMapping("/user/by-name/{username}")
  @PreAuthorize(ADMIN)
  @ApiOperation("Read a User by username (Admin-only)")
  public ResponseEntity<UserDTO> readByUsername(@PathVariable String username) {
    return ok(Optional.of(userService.getOne(username))
            .map(i -> toDTO(i, false))
            .map(CryptoConverter::encryptApiKeys)
            .orElseThrow()
    );
  }

  @GetMapping("/logins")
  @PreAuthorize(ADMIN)
  @ApiOperation("Get All Logins (Admin-only)")
  public ResponseEntity<List<LoginDTO>> getAllLogins() {
    return ok(loginRepository.findAllByTenantId(context.getTenantId())
        .stream()
        .map(AdminController::toLoginDTO)
        .sorted(comparing(LoginDTO::getId))
        .collect(toList())
    );
  }

  @GetMapping("/volume")
  @PreAuthorize(ADMIN)
  @ApiOperation("Calculate total and active balances, of the specified trader (Admin-only)")
  public ResponseEntity<Map<String, Double>> calculateTotalBalance(
      @RequestParam(TRADER_PARAM) String traderName
  ) {
    return ok(ImmutableMap.of(
        TOTAL_VOLUME, adminService.calculateTotalVolume(traderName),
        ACTIVE_VOLUME, adminService.calculateActiveVolume(traderName, ETHUSD)
    ));
  }

  @GetMapping("/balances")
  @PreAuthorize(ADMIN)
  @ApiOperation("Get the follower balances, of the specified trader (Admin-only)")
  public ResponseEntity<Map<String, Double>> getBalances(
      @RequestParam(TRADER_PARAM) String traderName
  ) {
    return ok(traderService.getFollowerBalances(traderName));
  }

  private static LoginDTO toLoginDTO(Login login) {
    return LoginDTO.builder()
        .id(login.getId())
        .createdOn(login.getCreatedOn())
        .user(login.getUser())
        .build();
  }
}
