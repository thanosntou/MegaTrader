package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.Member.MemberService;
import com.ntouzidis.cooperative.module.Offer.Offer;
import com.ntouzidis.cooperative.module.Offer.OfferService;
import com.ntouzidis.cooperative.module.Product.Product;
import com.ntouzidis.cooperative.module.Product.ProductService;
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

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.security.Principal;

@Controller
@RequestMapping("/offers")
public class OfferController {
    
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OfferService offerService;
    
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(
                Product.class,
                "product",
                new PropertyEditorSupport() {

                    @Override
                    public void setAsText(String text) {
                    Product product = productService.getById(Integer.parseInt(text));
                    setValue(product);
                    }

                }
        );
    }

    
    @GetMapping("/new-offer")
    public String showDetailsOfferForm(Model model){
        model.addAttribute("offer", new Offer());
        model.addAttribute("products", productService.getAllSortedAndOrdered("name", "asc"));
        return "offer-form";
    }
    
    @GetMapping("/save-offer")
    public String saveOffer(@Valid @ModelAttribute("offer") Offer theOffer,
                            BindingResult theBindingResult,
                            Model theModel,
                            Principal principal,
                            RedirectAttributes ra) {

        theOffer.setMember(memberService.getByUsername(principal.getName()));
        theOffer.setActive(0);
        offerService.save(theOffer);
        return "redirect:/management-panel/offers";
    }
    
    
    
}
