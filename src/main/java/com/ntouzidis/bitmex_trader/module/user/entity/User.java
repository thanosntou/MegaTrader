package com.ntouzidis.bitmex_trader.module.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntouzidis.bitmex_trader.module.common.attribute_converters.CryptoConverter;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.common.exceptions.NotFoundException;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Client;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "users") // used 'users' instead of 'user' because of spring security default queries. leave it!!
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @NotNull(message = " is required")
  @Pattern(
      regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      message = "Invalid email"
  )
  @Column(name = "email")
  private String email;

  @Column(name = "enabled")
  private Boolean enabled;

  @Column(name = "api_key")
  @Convert(converter = CryptoConverter.class)
  private String apiKey;

  @Column(name = "api_secret")
  @Convert(converter = CryptoConverter.class)
  private String apiSecret;

  private Client client;

  @Column(name = "created_on")
  private LocalDateTime createdOn = now();

  @JsonIgnore
  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = ALL)
  private List<QtyPreference> qtyPreferences;

  @JsonIgnore
  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = ALL, fetch = EAGER)
  private List<Authority> authorities;

  public User() {
  }

  public User(String username, String password) {
    this(username, password, true, true, true, true);
  }

  public User(String username, String password, boolean enabled,
              boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {

    if (((username == null) || "".equals(username)) || (password == null))
      throw new IllegalArgumentException("Cannot pass null or empty values to constructor");

    this.username = username;
    this.password = password;
  }

  public QtyPreference getQtyPreference(Symbol symbol) {
    return qtyPreferences.stream()
        .filter(i -> i.getSymbol().equals(symbol))
        .findFirst()
        .orElseThrow(() -> new NotFoundException("Qty preference not found"));
  }

  public void addQtyPreference(QtyPreference qtyPreference) {
    qtyPreferences.add(qtyPreference);
    qtyPreference.setUser(this);
  }

  public void removeQtyPreference(QtyPreference qtyPreference) {
    qtyPreferences.remove(qtyPreference);
    qtyPreference.setUser(null);
  }

  public void addAuthority(Authority authority) {
    authorities.add(authority);
    authority.setUser(this);
  }

  public void removeAuthority(Authority authority) {
    authorities.remove(authority);
    authority.setUser(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }

  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<QtyPreference> getQtyPreferences() {
    return qtyPreferences;
  }

  public void setQtyPreferences(List<QtyPreference> qtyPreferences) {
    this.qtyPreferences = qtyPreferences;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }
}
