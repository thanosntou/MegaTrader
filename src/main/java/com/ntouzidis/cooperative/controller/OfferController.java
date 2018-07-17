package com.ntouzidis.cooperative.controller;


import java.beans.PropertyEditorSupport;
import java.security.Principal;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OfferController {
    
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private OfferService offerService;
    
//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//	dataBinder.registerCustomEditor(Product.class,"product",
//        new PropertyEditorSupport() {
//
//            @Override
//            public void setAsText(String text) {
//                Product product = productService.getProduct(Integer.parseInt(text));
//                setValue(product);
//            }
//        });
//    }

    
    @GetMapping("/new-offer")
    public String showDetailsOfferForm(Model model){
//        model.addAttribute("offer", new Offer());
//        model.addAttribute("products", productService.getProducts());
        return "offer-form";
    }
    
//    @GetMapping("/save-offer")
//    public String saveOffer(@Valid @ModelAttribute("offer") Offer theOffer,
//                            BindingResult theBindingResult,
//                            Model theModel,
//                            Principal principal,
//                            RedirectAttributes ra) {
//
//        theOffer.setMember(memberService.getMemberByUsername(principal.getName()));
//        theOffer.setActive(0);
//        offerService.saveOffer(theOffer);
//        return "redirect:/management-panel#pills-offers";
//    }
    
    
    
}
