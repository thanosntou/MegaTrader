package com.ntouzidis.cooperative.module.user.wallet;

import com.ntouzidis.cooperative.module.user.User;
import javax.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;


    @Column(name="username")
    private Integer balance;

    public Wallet() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
