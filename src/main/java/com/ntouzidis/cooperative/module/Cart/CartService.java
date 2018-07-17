package com.ntouzidis.cooperative.module.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getAllSortedAndOrdered(String smb, String omb);
}
