package com.ntouzidis.cooperative.module.user.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer_to_trader")
public class CustomerToTraderLink {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name="customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name="trader_id")
    private User trader;

    @Column(name="create_date")
    private LocalDate create_date;

    @Column(name="guide")
    private boolean guide;

    @Column(name="hidden")
    private boolean hidden;

    public CustomerToTraderLink() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getTrader() {
        return trader;
    }

    public void setTrader(User trader) {
        this.trader = trader;
    }

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    public boolean isGuide() {
        return guide;
    }

    public void setGuide(boolean guide) {
        this.guide = guide;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
