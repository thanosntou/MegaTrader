package com.ntouzidis.cooperative.controller;


import java.security.Principal;

import com.ntouzidis.cooperative.module.cart.CartService;
import com.ntouzidis.cooperative.module.customer.CustomerService;
import com.ntouzidis.cooperative.module.sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sales")
public class OrderController {
    
    @Autowired
    private CustomerService customerService;
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private ProductService productService;
    @Autowired
    private SaleService saleService;
//    @Autowired
//    private UserDetailsManager userDetailsManager;
    @Autowired
    private CartService cartService;


    @GetMapping("/productDetails")
    public String showSalePage(@RequestParam("productId") int theId, Model model){
        return "product-buy-form";
    }


    @PostMapping("/checkout")
    public String checkout(Principal principal){
        saleService.orderWholeCart(customerService.getCart(principal.getName()));
        return "redirect:/shop";
    }
}
