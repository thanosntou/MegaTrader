package com.ntouzidis.cooperative.module.Sale;

import com.ntouzidis.cooperative.module.Cart.Cart;

import java.util.List;

public interface SaleService {

    List<Sale> getAllSortedAndOrdered(String smb, String omb);

    void orderWholeCart(List<Cart> allByCustomer);
}
