package com.ntouzidis.cooperative.module.api_v1;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.user.entity.Tenant;
import com.ntouzidis.cooperative.module.user.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/tenant")
public class TenantApiV1Controller {

  private final TenantService tenantService;

  public TenantApiV1Controller(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<Tenant> get(
      @RequestParam(name = "id", required = false) Integer id,
      @RequestParam(name = "name", required = false) String name
  ) {
    Preconditions.checkArgument(Objects.nonNull(id) || StringUtils.isNotBlank(name),
        "Id or Name must be present");
    Tenant tenant;
    if (Objects.nonNull(id))
      tenant = tenantService.getOne(id);
    else
      tenant = tenantService.getOne(name);

    return ResponseEntity.ok(tenant);
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<List<Tenant>> getAll() {
    return ResponseEntity.ok(tenantService.getAll());
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<Tenant> create(@RequestParam("name") String name) {
    Preconditions.checkArgument(StringUtils.isNotBlank(name));
    return ResponseEntity.ok(tenantService.create(name));
  }


}
