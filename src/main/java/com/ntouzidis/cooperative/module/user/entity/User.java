package com.ntouzidis.cooperative.module.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ntouzidis.cooperative.module.common.enumeration.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "users") // used 'users' instead of 'user' because of spring security default queries. leave it!!
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @NotNull(message=" is required")
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name="email")
    private String email;

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="create_date")
    private LocalDate create_date;

    @Column(name="apikey")
    private String apiKey;

    @Column(name="apisecret")
    private String apiSecret;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="wallet_id")
    @JsonIgnore
    private Wallet wallet;

    @Column(name = "fixed_qty")
    private long fixedQtyXBTUSD;
    @Column(name = "fixed_qty_xbtjpy")
    private long fixedQtyXBTJPY;
    @Column(name = "fixed_qty_adaz18")
    private long fixedQtyADAZ18;
    @Column(name = "fixed_qty_bchz18")
    private long fixedQtyBCHZ18;
    @Column(name = "fixed_qty_eosz18")
    private long fixedQtyEOSZ18;
    @Column(name = "fixed_qty_ethusd")
    private long fixedQtyETHUSD;
    @Column(name = "fixed_qty_ltc1818")
    private long fixedQtyLTCZ18;
    @Column(name = "fixed_qty_trxz18")
    private long fixedQtyTRXZ18;
    @Column(name = "fixed_qty_xrpz18")
    private long fixedQtyXRPZ18;
    @Column(name = "fixed_qty_xbtkrw")
    private long fixedQtyXBTKRW;

    @Enumerated(EnumType.STRING)
    @Column(name = "client")
    private Client client;

    public User() {
    }

    public User(String username, String password) {
        this(username, password, true, true, true, true);
    }

    public User(String username, String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }


    public String getUsername() {
        return this.username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date() {
        if (this.create_date == null)
            this.create_date = LocalDate.now();
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

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    public long getFixedQtyXBTUSD() {
        return fixedQtyXBTUSD;
    }

    public void setFixedQtyXBTUSD(long fixedQtyXBTUSD) {
        this.fixedQtyXBTUSD = fixedQtyXBTUSD;
    }

    public long getFixedQtyXBTJPY() {
        return fixedQtyXBTJPY;
    }

    public void setFixedQtyXBTJPY(long fixedQtyXBTJPY) {
        this.fixedQtyXBTJPY = fixedQtyXBTJPY;
    }

    public long getFixedQtyADAZ18() {
        return fixedQtyADAZ18;
    }

    public void setFixedQtyADAZ18(long fixedQtyADAZ18) {
        this.fixedQtyADAZ18 = fixedQtyADAZ18;
    }

    public long getFixedQtyBCHZ18() {
        return fixedQtyBCHZ18;
    }

    public void setFixedQtyBCHZ18(long fixedQtyBCHZ18) {
        this.fixedQtyBCHZ18 = fixedQtyBCHZ18;
    }

    public long getFixedQtyEOSZ18() {
        return fixedQtyEOSZ18;
    }

    public void setFixedQtyEOSZ18(long fixedQtyEOSZ18) {
        this.fixedQtyEOSZ18 = fixedQtyEOSZ18;
    }

    public long getFixedQtyETHUSD() {
        return fixedQtyETHUSD;
    }

    public void setFixedQtyETHUSD(long fixedQtyETHUSD) {
        this.fixedQtyETHUSD = fixedQtyETHUSD;
    }

    public long getFixedQtyLTCZ18() {
        return fixedQtyLTCZ18;
    }

    public void setFixedQtyLTCZ18(long fixedQtyLTCZ18) {
        this.fixedQtyLTCZ18 = fixedQtyLTCZ18;
    }

    public long getFixedQtyTRXZ18() {
        return fixedQtyTRXZ18;
    }

    public void setFixedQtyTRXZ18(long fixedQtyTRXZ18) {
        this.fixedQtyTRXZ18 = fixedQtyTRXZ18;
    }

    public long getFixedQtyXRPZ18() {
        return fixedQtyXRPZ18;
    }

    public void setFixedQtyXRPZ18(long fixedQtyXRPZ18) {
        this.fixedQtyXRPZ18 = fixedQtyXRPZ18;
    }

    public long getFixedQtyXBTKRW() {
        return fixedQtyXBTKRW;
    }

    public void setFixedQtyXBTKRW(long fixedQtyXBTKRW) {
        this.fixedQtyXBTKRW = fixedQtyXBTKRW;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "BitmexUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", create_date=" + create_date +
                ", apiKey='" + apiKey + '\'' +
                ", apiSecret='" + apiSecret + '\'' +
                ", wallet=" + wallet +
                ", fixedQtyXBTUSD=" + fixedQtyXBTUSD +
                ", fixedQtyXBTJPY=" + fixedQtyXBTJPY +
                ", fixedQtyADAZ18=" + fixedQtyADAZ18 +
                ", fixedQtyBCHZ18=" + fixedQtyBCHZ18 +
                ", fixedQtyEOSZ18=" + fixedQtyEOSZ18 +
                ", fixedQtyETHUSD=" + fixedQtyETHUSD +
                ", fixedQtyLTCZ18=" + fixedQtyLTCZ18 +
                ", fixedQtyTRXZ18=" + fixedQtyTRXZ18 +
                ", fixedQtyXRPZ18=" + fixedQtyXRPZ18 +
                ", fixedQtyXBTKRW=" + fixedQtyXBTKRW +
                ", client=" + client +
                '}';
    }
}
