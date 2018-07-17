package com.ntouzidis.cooperative.module.Sale;

import com.ntouzidis.cooperative.module.Customer.Customer;
import com.ntouzidis.cooperative.module.Product.Product;
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
@Table(name = "sales")
public class Sale implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="product")
    private Product product;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="customer")
    private Customer customer;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "price")
    private Double price;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="paid")
    private Integer paid;

    @Column(name="completed")
    private Integer completed;

    @Column(name="dateofc")
    private LocalDateTime dateofc;
    
    

    public Sale() {
    }

    public Sale(Product product, Customer customer, Double price, Integer quantity) {
        this.price = price;
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
        this.completed = 0;
        this.paid = 1;
        this.dateofc = LocalDateTime.now();
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getDateOfC() {
        return dateofc;
    }

    public void setDateOfC(LocalDateTime dateOfC) {
        this.dateofc = dateOfC;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", product=" + product +
                ", customer=" + customer +
                ", price=" + price +
                ", quantity=" + quantity +
                ", paid=" + paid +
                ", completed=" + completed +
                ", dateOfC=" + dateofc +
                '}';
    }
}
