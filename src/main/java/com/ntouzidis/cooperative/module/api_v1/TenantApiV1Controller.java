package com.ntouzidis.cooperative.module.api_v1;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.user.entity.Tenant;
import com.ntouzidis.cooperative.module.user.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static com.ntouzidis.cooperative.module.common.ControllerPathsConstants.TENANT_CONTROLLER_PATH;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.ID_PARAM;
import static com.ntouzidis.cooperative.module.common.ParamsConstants.NAME_PARAM;
import static com.ntouzidis.cooperative.module.common.RolesConstants.ROOT_ROLE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    value = TENANT_CONTROLLER_PATH,
    produces = APPLICATION_JSON_VALUE
)
public class TenantApiV1Controller {

  private static final String ALL_PATH = "/all";

  private final TenantService tenantService;

  public TenantApiV1Controller(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @GetMapping
  @PreAuthorize(ROOT_ROLE)
  public ResponseEntity<Tenant> read(
      @RequestParam(name = ID_PARAM, required = false) Integer id,
      @RequestParam(name = NAME_PARAM, required = false) String name
  ) {
    Preconditions.checkArgument(Objects.nonNull(id) || StringUtils.isNotBlank(name),
        "Id or Name must be present");

    Tenant tenant = Objects.nonNull(id)
        ? tenantService.getOne(id)
        : tenantService.getOne(name);

    return ResponseEntity.ok(tenant);
  }

  @GetMapping(ALL_PATH)
  @PreAuthorize(ROOT_ROLE)
  public ResponseEntity<List<Tenant>> readAll() {
    return ResponseEntity.ok(tenantService.getAll());
  }

  @PostMapping
  @PreAuthorize(ROOT_ROLE)
  public ResponseEntity<Tenant> create(
      @RequestParam(NAME_PARAM) String name
  ) {
    Preconditions.checkArgument(StringUtils.isNotBlank(name));
    return ResponseEntity.ok(tenantService.create(name));
  }
}
