package com.ntouzidis.cooperative.module.cart;

import com.ntouzidis.cooperative.module.product.Product;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "CartProduct")
@Table(name = "cart_product")
public class CartProduct {

    @EmbeddedId
    private CartProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    private CartProduct() {}

    public CartProduct(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
        this.id = new CartProductId(cart.getId(), product.getId());
    }

    //Getters and setters omitted for brevity


    public CartProductId getId() {
        return id;
    }

    public void setId(CartProductId id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CartProduct that = (CartProduct) o;
        return Objects.equals(cart, that.cart) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, product);
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
