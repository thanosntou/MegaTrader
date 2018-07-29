package com.ntouzidis.cooperative.module.product;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(
//            name="product_id_seq",
//            sequenceName="product_id_seq",
//            allocationSize=20
//    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "category")
    private String category;

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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", priceShop=" + priceShop +
                ", priceBuy=" + priceBuy +
                ", quantity=" + quantity +
                '}';
    }
}