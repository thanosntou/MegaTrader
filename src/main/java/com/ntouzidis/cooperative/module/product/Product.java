package com.ntouzidis.cooperative.module.product;

import com.ntouzidis.cooperative.module.cart.Cart;
import com.ntouzidis.cooperative.module.cart.CartProduct;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "product")
@NaturalIdCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "category")
    private String category;

    @NaturalId
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation

    @Column(name = "priceShop")
    private Double priceShop;

    @Column(name = "priceBuy")
    private Double priceBuy;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;

//    @OneToMany(
//            mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<CartProduct> carts = new ArrayList<>();

    public Product() {
    }

    public Product(String category_id, String name, Double priceShop, Double priceBuy) {
        this.category = category_id;
        this.name = name;
        this.priceShop = priceShop;
        this.priceBuy = priceBuy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceShop() {
        return priceShop;
    }

    public void setPriceShop(Double priceShop) {
        this.priceShop = priceShop;
    }

    public Double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(Double priceBuy) {
        this.priceBuy = priceBuy;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<CartProduct> getCarts() {
//        return carts;
//    }
//
//    public void setCarts(List<CartProduct> carts) {
//        this.carts = carts;
//    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", priceShop=" + priceShop +
                ", priceBuy=" + priceBuy +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}