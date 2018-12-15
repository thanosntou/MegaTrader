package com.ntouzidis.cooperative.module.user.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "users") // used 'users' instead of 'user' because of spring security default queries. leave it!!
public class User {

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
    private Wallet wallet;

    @Column(name = "fixed_qty")
    private Long fixedQtyXBTUSD;
    @Column(name = "fixed_qty_xbtjpy")
    private Long fixedQtyXBTJPY;
    @Column(name = "fixed_qty_adaz18")
    private Long fixedQtyADAZ18;
    @Column(name = "fixed_qty_bchz18")
    private Long fixedQtyBCHZ18;
    @Column(name = "fixed_qty_eosz18")
    private Long fixedQtyEOSZ18;
    @Column(name = "fixed_qty_ethusd")
    private Long fixedQtyETHUSD;
    @Column(name = "fixed_qty_ltc1818")
    private Long fixedQtyLTCZ18;
    @Column(name = "fixed_qty_trxz18")
    private Long fixedQtyTRXZ18;
    @Column(name = "fixed_qty_xrpz18")
    private Long fixedQtyXRPZ18;
    @Column(name = "fixed_qty_xbtkrw")
    private Long fixedQtyXBTKRW;

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

    public Long getFixedQtyXBTUSD() {
        return fixedQtyXBTUSD;
    }

    public void setFixedQtyXBTUSD(Long fixedQtyXBTUSD) {
        this.fixedQtyXBTUSD = fixedQtyXBTUSD;
    }

//    public Long getFixedQtyBCHZ18() {
//        return fixedQtyBCHZ18;
//    }
//
//    public void setFixedQtyBCHZ18(Long fixedQtyBCHZ18) {
//        this.fixedQtyBCHZ18 = fixedQtyBCHZ18;
//    }

    public Long getFixedQtyEOSZ18() {
        return fixedQtyEOSZ18;
    }

    public void setFixedQtyEOSZ18(Long fixedQtyEOSZ18) {
        this.fixedQtyEOSZ18 = fixedQtyEOSZ18;
    }

    public Long getFixedQtyETHUSD() {
        return fixedQtyETHUSD;
    }

    public void setFixedQtyETHUSD(Long fixedQtyETHUSD) {
        this.fixedQtyETHUSD = fixedQtyETHUSD;
    }

    public Long getFixedQtyLTCZ18() {
        return fixedQtyLTCZ18;
    }

    public void setFixedQtyLTCZ18(Long fixedQtyLTCZ18) {
        this.fixedQtyLTCZ18 = fixedQtyLTCZ18;
    }

    public Long getFixedQtyXBTJPY() {
        return fixedQtyXBTJPY;
    }

    public void setFixedQtyXBTJPY(Long fixedQtyXBTJPY) {
        this.fixedQtyXBTJPY = fixedQtyXBTJPY;
    }

    public Long getFixedQtyADAZ18() {
        return fixedQtyADAZ18;
    }

    public void setFixedQtyADAZ18(Long fixedQtyADAZ18) {
        this.fixedQtyADAZ18 = fixedQtyADAZ18;
    }

    public Long getFixedQtyBCHZ18() {
        return fixedQtyBCHZ18;
    }

    public void setFixedQtyBCHZ18(Long fixedQtyBCHZ18) {
        this.fixedQtyBCHZ18 = fixedQtyBCHZ18;
    }

    public Long getFixedQtyTRXZ18() {
        return fixedQtyTRXZ18;
    }

    public void setFixedQtyTRXZ18(Long fixedQtyTRXZ18) {
        this.fixedQtyTRXZ18 = fixedQtyTRXZ18;
    }

    public Long getFixedQtyXRPZ18() {
        return fixedQtyXRPZ18;
    }

    public void setFixedQtyXRPZ18(Long fixedQtyXRPZ18) {
        this.fixedQtyXRPZ18 = fixedQtyXRPZ18;
    }

    public Long getFixedQtyXBTKRW() {
        return fixedQtyXBTKRW;
    }

    public void setFixedQtyXBTKRW(Long fixedQtyXBTKRW) {
        this.fixedQtyXBTKRW = fixedQtyXBTKRW;
    }
}
