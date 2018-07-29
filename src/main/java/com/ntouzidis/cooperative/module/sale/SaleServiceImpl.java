package com.ntouzidis.cooperative.module.sale;

import com.ntouzidis.cooperative.module.cart.Cart;
import com.ntouzidis.cooperative.module.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Sale> getAllSortedAndOrdered(String smb, String omb) {
        Sort sort = new Sort((omb.equals("asc")?Sort.Direction.ASC:Sort.Direction.DESC), smb);
        return saleRepository.findAll(sort);
    }

    //TODO: implement the paying logic
    @Override
    public void orderWholeCart(List<Cart> cart) {
        cartRepository.deleteInBatch(cart);

    }
}
