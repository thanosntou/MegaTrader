package com.ntouzidis.cooperative.controller;

import java.security.Principal;
import java.util.List;

import com.ntouzidis.cooperative.module.cart.Cart;
import com.ntouzidis.cooperative.module.cart.CartService;
import com.ntouzidis.cooperative.module.customer.Customer;
import com.ntouzidis.cooperative.module.customer.CustomerService;
import com.ntouzidis.cooperative.module.product.Product;
import com.ntouzidis.cooperative.module.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String showCart(Model model, Principal principal){
        Customer c = customerService.getOne(principal.getName());
        List<Cart> cart = cartService.getAllByCustomer(c);
        model.addAttribute("cart", cart);
        return "cart";
    }
    
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int id,
                            @RequestParam("quantity") int theQuantity,
                            Principal principal,
                            RedirectAttributes ra){
        
        Product p = productService.getById(id);
        Customer c = customerService.getOne(principal.getName());
        Cart newCartProduct = new Cart(c, p ,theQuantity);
        cartService.saveCart(newCartProduct);

        ra.addFlashAttribute("messageFromCart", "Product added to cart successfully.");
        return "redirect:/shop";
    }

    
    
    
}
