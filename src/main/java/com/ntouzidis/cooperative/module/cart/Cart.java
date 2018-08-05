package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product")
    private List<Product> products =  new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer", unique = true)
    @MapsId
    private Customer customer;

    public Cart() {
    }

    public Cart(Customer customer, Product product, Integer quantity) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}