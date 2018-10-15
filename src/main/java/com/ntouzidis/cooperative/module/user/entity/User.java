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
    private Long fixedQty;

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

    public Long getFixedQty() {
        return fixedQty;
    }

    public void setFixedQty(Long fixedQty) {
        this.fixedQty = fixedQty;
    }
}
