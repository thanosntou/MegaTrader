package com.ntouzidis.bitmex_trader.module.controller;

import com.ntouzidis.bitmex_trader.module.common.dto.UserDTO;
import com.ntouzidis.bitmex_trader.module.common.forms.AdminForm;
import com.ntouzidis.bitmex_trader.module.common.utils.UserUtils;
import com.ntouzidis.bitmex_trader.module.user.entity.Tenant;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import com.ntouzidis.bitmex_trader.module.user.service.RootService;
import com.ntouzidis.bitmex_trader.module.user.service.TenantService;
import com.ntouzidis.bitmex_trader.module.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.ntouzidis.bitmex_trader.module.common.constants.AuthorizationConstants.ROOT;
import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.ROOT_CONTROLLER_PATH;
import static com.ntouzidis.bitmex_trader.module.common.utils.UserUtils.toDTO;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    value = ROOT_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
@Api(tags = "Root API")
@RequiredArgsConstructor
public class RootController {

  private final UserService userService;
  private final RootService rootService;
  private final TenantService tenantService;

  @GetMapping("/users")
  @PreAuthorize(ROOT)
  @ApiOperation("Read all Users")
  public ResponseEntity<List<UserDTO>> readAllUsers() {
    return ok(userService.getAll()
            .stream()
            .sorted((user1, user2) -> {
              int u1Hierarchy = getHierarchy(user1);
              Integer u2Hierarchy = getHierarchy(user2);
              return u2Hierarchy.compareTo(u1Hierarchy);
            })
            .map(UserUtils::toDTOForRoot)
            .collect(toList())
    );
  }

  @GetMapping("/tenants")
  @PreAuthorize(ROOT)
  @ApiOperation("Read all Tenants")
  public ResponseEntity<List<Tenant>> readAllTenants() {
    return ok(tenantService.getAll());
  }

  @GetMapping("/tenants/{id}")
  @PreAuthorize(ROOT)
  @ApiOperation(value = "Read one Tenant by ID")
  public ResponseEntity<Tenant> readTenant(@PathVariable Long id) {
    return ok(tenantService.getOne(id));
  }

  @PostMapping("/tenants")
  @PreAuthorize(ROOT)
  @ApiOperation("Create a new Tenant")
  public ResponseEntity<Tenant> createTenant(@RequestBody String name) {
    return ok(tenantService.create(name));
  }

  @PostMapping("/admin")
  @PreAuthorize(ROOT)
  @ApiOperation("Create a new Admin")
  public ResponseEntity<UserDTO> createAdmin(@RequestBody @Valid AdminForm adminForm) {
    Tenant tenant = tenantService.getOne(adminForm.getTenantId());
    User user = userService.createAdmin(tenant, adminForm);
    return ok(toDTO(user, true));
  }

  @GetMapping("/tenants/{tenantId}/admins")
  @PreAuthorize(ROOT)
  @ApiOperation("Read the Traders of a specific Tenant")
  public ResponseEntity<List<User>> readAllAdminsByTenant(@PathVariable Long tenantId) {
    return ok(userService.getAdminsByTenant(tenantId));
  }

  @GetMapping("/tenants/{tenantId}/traders")
  @PreAuthorize(ROOT)
  @ApiOperation("Read the Traders of a specific Tenant")
  public ResponseEntity<List<User>> readAllTradersByTenant(@PathVariable Long tenantId) {
    return ok(userService.getTradersByTenant(tenantId));
  }

  @GetMapping("/traders/{traderId}/followers")
  @PreAuthorize(ROOT)
  @ApiOperation("Read the Followers of a specific trader")
  public ResponseEntity<List<User>> readAllFollowersByTrader(@PathVariable Long traderId) {
    return ok(userService.getFollowersByTrader(traderId));
  }

  @DeleteMapping("/users/{id}")
  @PreAuthorize(ROOT)
  @ApiOperation("Delete a User")
  public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable Long id) {
    userService.delete(userService.getOne(id));
    return readAllUsers();
  }

  @DeleteMapping("/followers/{id}")
  @PreAuthorize(ROOT)
  @ApiOperation("Delete a Follower User")
  public ResponseEntity<UserDTO> deleteFollowerUser(@PathVariable Long id) {
    return ok(toDTO(rootService.deleteFollowerUser(null, id), false));
  }

  @DeleteMapping("/traders/{id}")
  @PreAuthorize(ROOT)
  @ApiOperation("Delete a Trader User")
  public ResponseEntity<UserDTO> deleteTraderUser(@PathVariable Long id) {
    return ok(toDTO(rootService.deleteTraderUser(null, id), false));
  }

  @DeleteMapping("/admins/{id}")
  @PreAuthorize(ROOT)
  @ApiOperation("Delete a Admin User")
  public ResponseEntity<UserDTO> deleteAdminUser(@PathVariable Long id) {
    return ok(toDTO(rootService.deleteAdminUser(null, id), false));
  }

  @DeleteMapping("/tenants/{id}")
  @PreAuthorize(ROOT)
  @ApiOperation("Delete a Tenant")
  public ResponseEntity<Tenant> deleteTenant(@PathVariable Long id) {
    return ok(rootService.deleteTenant(id));
  }

  private int getHierarchy(User user) {
    if (UserUtils.isAdmin(user))
      return 3;
    else if (UserUtils.isTrader(user))
      return 2;
    else if (UserUtils.isFollower(user))
      return 1;
    else
      return 0;
  }

}
