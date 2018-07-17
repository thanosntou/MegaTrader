package com.ntouzidis.cooperative.controller;


import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpSession;
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
    
//    @Autowired
//    private CustomerService customerService;
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private SaleService saleService;
//    @Autowired
//    private UserDetailsManager userDetailsManager;
//    @Autowired
//    private CartService cartService;


    @GetMapping("/productDetails")
    public String showSalePage(@RequestParam("productId") int theId, Model model){
        return "product-buy-form";
    }


    @PostMapping("/checkout")
    public String checkout(HttpSession session, Principal principal){
//        List<Cart> cart = cartService.getProductsOfCart(customerService.getCustomerByUsername(principal.getName()));
//        saleService.orderWholeCart(cart);
        return "redirect:/shop";
    }
}
