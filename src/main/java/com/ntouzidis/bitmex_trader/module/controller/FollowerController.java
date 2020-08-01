package com.ntouzidis.bitmex_trader.module.controller;

import com.ntouzidis.bitmex_trader.module.common.dto.UserDTO;
import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import com.ntouzidis.bitmex_trader.module.common.forms.UserUpdateForm;
import com.ntouzidis.bitmex_trader.module.common.pojo.Context;
import com.ntouzidis.bitmex_trader.module.common.utils.UserUtils;
import com.ntouzidis.bitmex_trader.module.trade.service.IBitmexApiService;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ntouzidis.bitmex_trader.module.common.constants.AuthorizationConstants.*;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.*;
import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.*;
import static com.ntouzidis.bitmex_trader.module.common.utils.UserUtils.toDTO;
import static com.ntouzidis.bitmex_trader.module.common.utils.UserUtils.toDTOForFollower;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    value = FOLLOWER_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
@Api(tags = "Follower API")
@RequiredArgsConstructor
public class FollowerController {

  private final Context context;
  private final UserService userService;
  private final IBitmexApiService bitmexService;

  @GetMapping("/dashboard")
  @PreAuthorize(FOLLOWER)
  @ApiOperation("Get you bitmex account informations")
  public ResponseEntity<Map<String, String>> getDashboardInfo() {
    if (isBlank(context.getUser().getApiKey()) || isBlank(context.getUser().getApiSecret()))
      return ResponseEntity.ok().build();

    return ok(of(bitmexService.getUserMargin(context.getUser()))
        .map(userMarginDetails -> {
          Map<String, String> dashboardInfos = new HashMap<>();
          dashboardInfos.put("client", context.getUser().getClient().getValue());
          dashboardInfos.put("earned", "0");
          if (!userMarginDetails.isEmpty()) {
            String walletBalance = userMarginDetails.get(WALLET_BALANCE).toString();
            String availableMargin = userMarginDetails.get(AVAILABLE_MARGIN).toString();
            String activeBalance = valueOf(parseInt(walletBalance) - parseInt(availableMargin));

            dashboardInfos.put(WALLET_BALANCE, walletBalance);
            dashboardInfos.put(AVAILABLE_MARGIN, availableMargin);
            dashboardInfos.put(ACTIVE_BALANCE, activeBalance);
          }
          return dashboardInfos;
        })
        .orElseThrow(() -> new NotFoundException("User margin not found")));
  }

  @GetMapping("/traders")
  @PreAuthorize(FOLLOWER)
  @ApiOperation("Read all available Traders")
  public ResponseEntity<List<UserDTO>> readAll() {
    return ok(userService.getTraders()
        .stream()
        .map(UserUtils::toDTOForFollower)
        .collect(toList())
    );
  }

  @GetMapping("/trader")
  @PreAuthorize(FOLLOWER)
  @ApiOperation("Read the Trader you are following")
  public ResponseEntity<UserDTO> getFollowingTrader() {
    User trader = userService.getPersonalTrader(context.getUser())
            .orElseThrow(() -> new RuntimeException("You are not following any trader yet"));
    return ok(toDTOForFollower(trader));
  }

  @PostMapping("/trader")
  @PreAuthorize(FOLLOWER)
  @ApiOperation("Follow a Trader")
  public ResponseEntity<UserDTO> followTrader(@RequestParam Long traderId) {
    return ok(toDTOForFollower(userService.linkTrader(context.getUserID(), traderId)));
  }

  @DeleteMapping("/trader")
  @PreAuthorize(FOLLOWER)
  @ApiOperation("Unfollow a Trader")
  public ResponseEntity<Void> unFollowTrader() {
    User follower = context.getUser();
    userService.unlinkTrader(follower);
    return ok().build();
  }

  @PutMapping
  @PreAuthorize(FOLLOWER)
  @ApiOperation("Update your info and settings")
  public ResponseEntity<UserDTO> updatePersonalInfo(@RequestBody @Valid UserUpdateForm userUpdateForm) {
    return ok(toDTO(userService.update(context.getUserID(), userUpdateForm), false));
  }

}
