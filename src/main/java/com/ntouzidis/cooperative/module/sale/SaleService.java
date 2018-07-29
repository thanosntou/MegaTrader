package com.ntouzidis.cooperative.module.sale;

import com.ntouzidis.cooperative.module.cart.Cart;

import java.util.List;

public interface SaleService {

    List<Sale> getAllSortedAndOrdered(String smb, String omb);

    void orderWholeCart(List<Cart> allByCustomer);
}
