package com.ntouzidis.cooperative.controller;

import java.util.List;

import com.ntouzidis.cooperative.module.category.CategoryService;
import com.ntouzidis.cooperative.module.product.Product;
import com.ntouzidis.cooperative.module.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {
    
    @Autowired
    private ProductService ProductService;

    @Autowired
    private CategoryService categoryService;
    
    @RequestMapping("/")
    public String showWelcomePage(){
        return "welcome";
    }
    
    @GetMapping("/shop")
    public String showShop(Model model){
        List<Product> products = ProductService.findAll();
        List<Product> fruits = ProductService.findAllByCategory("fruits");
        List<Product> vegetables = ProductService.findAllByCategory("vegetables");
        List<Product> mushrooms = ProductService.findAllByCategory("mushrooms");
//        List<Category> categories = categoryService.getCategories();
        model.addAttribute("products", products);
        model.addAttribute("fruits", fruits);
        model.addAttribute("vegetables", vegetables);
        model.addAttribute("mushrooms", mushrooms);
//        model.addAttribute("categories", categories);
        return "shop";
    }
    
    @GetMapping("/shop/productDetails")
    public String showProductDetails(@RequestParam("productId") int theId, Model model){
        model.addAttribute("product", ProductService.getById(theId));
        return "product-buy-form";
    }
    
    @PostMapping("/shop/productDetails")
    public String addProductToCart(@RequestParam("productId") int theId, Model model){
        model.addAttribute("product", ProductService.getById(theId));
        model.addAttribute("addSuccessfully", "Product added to cart");
        return "product-buy-form";
    }
}