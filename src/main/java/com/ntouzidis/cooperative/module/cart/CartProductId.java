package com.ntouzidis.cooperative.module.cart;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartProductId implements Serializable {

    @Column(name = "cart_id")
    private Integer cart_id;

    @Column(name = "product_id")
    private Integer product_id;

    private CartProductId() {}

    public CartProductId(Integer cart_id, Integer product_id) {
        this.cart_id = cart_id;
        this.product_id = product_id;
    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    //Getters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CartProductId that = (CartProductId) o;
        return Objects.equals(cart_id, that.cart_id) &&
                Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart_id, product_id);
    }
}