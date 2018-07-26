package com.ntouzidis.cooperative.module.Payment;

import com.ntouzidis.cooperative.module.Member.Member;
import com.ntouzidis.cooperative.module.Sale.Sale;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="sale")
    private Sale sale;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="member")
    private Member member;

    @Column(name="amount")
    private Double amount;

    @Column(name="dateofc")
    private LocalDateTime date;

    public Payment() {
    }

    public Payment(Sale sale, Member member, Double amount) {
        this.sale = sale;
        this.member = member;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    
    
    
    
    
}
