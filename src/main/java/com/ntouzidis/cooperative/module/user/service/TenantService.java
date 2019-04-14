package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.common.NotFoundException;
import com.ntouzidis.cooperative.module.user.entity.Tenant;
import com.ntouzidis.cooperative.module.user.repository.TenantRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TenantService {

  private static final String NOT_FOUND = "Tenant not found";

  private final TenantRepository tenantRepository;

  public TenantService(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  public Tenant getOne(Integer id) {
    return tenantRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND));
  }

  public Tenant getOne(String name) {
    return tenantRepository.findByName(name).orElseThrow(() -> new NotFoundException(NOT_FOUND));
  }

  public List<Tenant> getAll() {
    return tenantRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
  }

  @Transactional
  public Tenant create(String name) {
    Tenant tenant = new Tenant();
    tenant.setName(name);
    tenant.setCreate_date(LocalDate.now());
    return tenantRepository.save(tenant);
  }
}
