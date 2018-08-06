package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.product.Product;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartProduct> products = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;

    public Cart() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public void addProduct(Product product) {
        CartProduct cartProduct = new CartProduct(this, product);
        products.add(cartProduct);
//        product.getCarts().add(cartProduct);
    }

    public void removeProduct(Product tag) {
        for (Iterator<CartProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            CartProduct cartProduct = iterator.next();

            if (cartProduct.getCart().equals(this) &&
                    cartProduct.getProduct().equals(tag)) {
                iterator.remove();
//                cartProduct.getProduct().getCarts().remove(cartProduct);
                cartProduct.setCart(null);
                cartProduct.setCart(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", products=" + products +
                ", customer=" + customer +
                '}';
    }
}