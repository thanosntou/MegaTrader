package com.ntouzidis.cooperative.controller;

import java.util.List;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = {"", "/", "/products"})
    public String showProducts(@RequestParam(name="sortBy", defaultValue = "name") String sb,
                               @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                               Model model) {

        List<Product> products = productService.getSortedBy(sb, ob);
        model.addAttribute("businessEntity", "products");
        model.addAttribute("businessList", products);
        return "management-panel";
    }

    @GetMapping("/customers")
    public String showCustomers(@RequestParam(name="sortBy", defaultValue = "username") String sb,
                                @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                                Model model) {

        List<Customer> customers = customerService.getSortedAndOrdered(sb, ob);
        model.addAttribute("businessEntity", "customers");
        model.addAttribute("businessList", customers);
        return "management-panel";
    }

    @GetMapping("/members")
    public String showMembers(@RequestParam(name="sortBy", defaultValue = "username") String sb,
                              @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                              Model model) {

        List<Member> members = memberService.getAllSortedAndOrdered(sb, ob);
        model.addAttribute("businessEntity", "members");
        model.addAttribute("businessList", members);
        return "management-panel";
    }

    @GetMapping("/sales")
    public String showSales(@RequestParam(name="sortBy", defaultValue = "dateofc") String sb,
                            @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                            Model model) {

        List<Sale> sales = saleService.getAllSortedAndOrdered(sb, ob);
        model.addAttribute("businessEntity", "sales");
        model.addAttribute("businessList", sales);
        return "management-panel";
    }

    @GetMapping("/offers")
    public String showoffers(@RequestParam(name="sortBy", defaultValue = "product.id") String sb,
                             @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                             Model model) {

        List<Offer> offers = offerService.getAllSortedAndOrdered(sb, ob);
        model.addAttribute("businessEntity", "offers");
        model.addAttribute("businessList", offers);
        return "management-panel";
    }

    @GetMapping("/payments")
    public String showPayments(@RequestParam(name="sortBy", defaultValue = "date") String sb,
                               @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                               Model model) {

        List<Payment> payments = paymentService.getAllSortedAndOrdered(sb, ob);
        model.addAttribute("businessEntity", "payments");
        model.addAttribute("businessList", payments);
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