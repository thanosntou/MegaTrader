/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntouzidis.cooperative.controller;

import com.ntouzidis.cooperative.module.Customer.CustomerService;
import com.ntouzidis.cooperative.module.Offer.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Athan
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private OfferService offerService;
    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/activate")
    public String activateOffer(@RequestParam("offerId") String offerId){
        offerService.activate(Integer.parseInt(offerId));
        return "redirect:/management-panel/offers";
    }
    
    @GetMapping("/deactivate")
    public String deactivateOffer(@RequestParam("offerId") String offerId){
        offerService.deactivate(Integer.parseInt(offerId));
        return "redirect:/management-panel/offers";
    }
    
    @GetMapping("/disable")
    public String disableCustomer(@RequestParam("customerId") String customerId){
//        customerService.disable(Integer.parseInt(customerId));
        return "redirect:/management-panel/offers";
    }
}
