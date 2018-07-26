package com.ntouzidis.cooperative.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.ntouzidis.cooperative.module.Admin.AdminService;
import com.ntouzidis.cooperative.module.Category.CategoryService;
import com.ntouzidis.cooperative.module.Customer.Customer;
import com.ntouzidis.cooperative.module.Customer.CustomerService;
import com.ntouzidis.cooperative.module.Member.Member;
import com.ntouzidis.cooperative.module.Member.MemberService;
import com.ntouzidis.cooperative.module.Offer.Offer;
import com.ntouzidis.cooperative.module.Offer.OfferService;
import com.ntouzidis.cooperative.module.Payment.Payment;
import com.ntouzidis.cooperative.module.Payment.PaymentService;
import com.ntouzidis.cooperative.module.Product.Product;
import com.ntouzidis.cooperative.module.Product.ProductService;
import com.ntouzidis.cooperative.module.Sale.Sale;
import com.ntouzidis.cooperative.module.Sale.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/management-panel")
public class ManagementPanelController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = {"", "/"})
    public String showProducts(@RequestParam(name="sortBy", defaultValue = "name") String sb,
                               @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                               Model model, Principal principal) {

        List<Product> products = productService.getAllSortedAndOrdered(sb, ob);
        model.addAttribute("businessEntity", "products");
        model.addAttribute("businessList", products);
        model.addAttribute(
                "user",
                (principal.getName().equalsIgnoreCase("athan")
                        ?adminService.getByUsername(principal.getName())
                        :memberService.getByUsername(principal.getName()))
        );
        return "management-panel";
    }

    @GetMapping(value = {"/{tab}"})
    public String showTab(@PathVariable(name = "tab") String tab,
                          @RequestParam(name="sortBy") String sb,
                          @RequestParam(name="orderBy") String ob,
                               Model model, Principal principal) {

        List<?> tabContent = null;

        if (tab.equals("products")) {
            tabContent = productService.getAllSortedAndOrdered(sb, ob);
        } else if (tab.equals("customers")) {
            tabContent = customerService.getSortedAndOrdered(sb, ob);
        } else if (tab.equals("members")) {
            tabContent = memberService.getAllSortedAndOrdered(sb, ob);
        } else if (tab.equals("sales")) {
            tabContent = saleService.getAllSortedAndOrdered(sb, ob);
        } else if (tab.equals("offers")) {
            tabContent = offerService.getAllSortedAndOrdered(sb, ob);
        } else if (tab.equals("payments")) {
            tabContent = paymentService.getAllSortedAndOrdered(sb, ob);
        }

        model.addAttribute("businessEntity", tab);
        model.addAttribute("businessList", tabContent);
        model.addAttribute("user", (principal.getName().equalsIgnoreCase("athan")
                ?adminService.getByUsername(principal.getName())
                :memberService.getByUsername(principal.getName()))
        );

        return "management-panel";
    }

//    @GetMapping("/new-product")
//    public String newProduct(ModelMap model){
//        model.addAttribute("product", new Product());
//        model.addAttribute("theCategories", categoryService.getCategories());
//        return "product-form";
//    }
//
//    @GetMapping("/updateProduct")
//    public String updateProduct(@RequestParam("productId") int theId, Model theModel) {
//	Product theProduct = productService.getProduct(theId);
//	theModel.addAttribute("product", theProduct);
//        theModel.addAttribute("theCategories", categoryService.getCategories());
//	return "product-form";
//    }
//
//    @PostMapping("/save-product")
//    public String submit(
//            @Valid @ModelAttribute("product") Product theProduct,
//            @RequestParam(required=false,name="myfile") MultipartFile file, ModelMap model,
//            HttpServletRequest request) {
//
////        model.addAttribute("file", file);
//        productService.saveProduct(theProduct);
//
//        try {
//            byte[] bytes = file.getBytes();
//            String s = request.getContextPath();
//            String s2 = (request.getContextPath() + "/resources/images/" + file.getOriginalFilename());
//            model.addAttribute("info", s);
//            model.addAttribute("info2", s2);
//            Path path = Paths.get("//home//athan//Desktop//cooperativeeshop//src//main//webapp//resources//images//" + file.getOriginalFilename());
//            Files.write(path, bytes);
//        } catch (IOException ex) {
//            Logger.getLogger(ManagementPanelController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "redirect:/management-panel";
//    }
    

//    @GetMapping("/updateCustomer")
//    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
//	Customer theCustomer = customerService.getCustomer(theId);
//	theModel.addAttribute("customer", theCustomer);
//	return "registration-form-customer";
//    }
//
//    @GetMapping("/deleteCustomer")
//    public String deleteCustomer(@RequestParam("customerId") int theId) {
//	customerService.deleteCustomer(theId);
//	return "redirect:/shop";
//    }
//
//
//
//    @GetMapping("/deleteProduct")
//    public String deleteProduct(@RequestParam("productId") int theId) {
//	productService.deleteProduct(theId);
//	return "redirect:/shop";
//    }
//
//    @GetMapping("/payment")
//    public String getPayments(Model model){
//        List<Payment> payments2 = paymentService.getPayments();
//        model.addAttribute("payments2", payments2);
//        return "management-panel";
//    }
    
}