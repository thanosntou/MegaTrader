package com.ntouzidis.cooperative.controller;

import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.List;
import com.ntouzidis.cooperative.module.admin.AdminService;
import com.ntouzidis.cooperative.module.category.CategoryService;
import com.ntouzidis.cooperative.module.customer.CustomerService;
import com.ntouzidis.cooperative.module.member.MemberService;
import com.ntouzidis.cooperative.module.offer.OfferService;
import com.ntouzidis.cooperative.module.payment.PaymentService;
import com.ntouzidis.cooperative.module.product.ProductService;
import com.ntouzidis.cooperative.module.product.Product;
import com.ntouzidis.cooperative.module.sale.SaleService;
import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;


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
    private ProductService ProductService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private OfferService offerService;
    @Autowired
    private PaymentService paymentService;

    @Value("${images.upload.folder}")
    private String imagesfolder;

    @GetMapping(value = {"", "/"})
    public String showProducts(@RequestParam(name="sortBy", defaultValue = "name") String sb,
                               @RequestParam(name="orderBy", defaultValue = "asc") String ob,
                               Model model, Principal principal) {

        List<Product> products = ProductService.getAllSortedAndOrdered(sb, ob);
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
                          @RequestParam(name="sortBy", required = false, defaultValue = "username") String sb,
                          @RequestParam(name="orderBy", required = false, defaultValue = "asc") String ob,
                               Model model, Principal principal) {

        List<?> tabContent = null;

        if (tab.equals("products")) {
            tabContent = ProductService.getAllSortedAndOrdered((sb.equals("username")?"name":sb), ob);
        } else if (tab.equals("customers")) {
            tabContent = customerService.getSortedAndOrdered(sb, ob);
        } else if (tab.equals("members")) {
            tabContent = memberService.getAllSortedAndOrdered(sb, ob);
        } else if (tab.equals("sales")) {
            tabContent = saleService.getAllSortedAndOrdered((sb.equals("username")?"dateofc":sb), ob);
            sb = "name";
        } else if (tab.equals("offers")) {
            tabContent = offerService.getAllSortedAndOrdered((sb.equals("username")?"id":sb), ob);
        } else if (tab.equals("payments")) {
            tabContent = paymentService.getAllSortedAndOrdered((sb.equals("username")?"date":sb), ob);
        }

        model.addAttribute("businessEntity", tab);
        model.addAttribute("businessList", tabContent);
        model.addAttribute("user", (principal.getName().equalsIgnoreCase("athan")
                ?adminService.getByUsername(principal.getName())
                :memberService.getByUsername(principal.getName()))
        );

        return "management-panel";
    }

    @GetMapping("/new-product")
    public String newProduct(ModelMap model){
        model.addAttribute("product", new Product());
        model.addAttribute("theCategories", categoryService.getAllSortedAndOrdered("name", "asc"));
        return "product-form";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId") int id, Model theModel) {
        theModel.addAttribute("product", ProductService.getById(id));
        theModel.addAttribute("theCategories", categoryService.getAllSortedAndOrdered("name", "asc"));
	    return "product-form";
    }

    @PostMapping("/save-product")
    public String submit(
            @Valid @ModelAttribute("product") Product theProduct,
            @RequestParam(required=false,name="myfile") MultipartFile file) throws IOException {

        ProductService.saveOrUpdate(theProduct);

        //TODO: need refactoring here, move logic
        if (!file.isEmpty() && ImageIO.read(file.getInputStream()) != null) {
            Path pathFile = Paths.get("src/main/resources/static/images/" + theProduct.getName() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
            Files.write(pathFile, file.getBytes(), StandardOpenOption.CREATE);
        }

        return "redirect:/management-panel";
    }

    //TODO: maybe change to delete requests, but have to figure out jstl delete reqs...
    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") int theId) {
	    ProductService.delete(theId);
	    return "redirect:/management-panel/products";
    }

//    @GetMapping("/updateCustomer")
//    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
//	    Customer theCustomer = customerService.getCustomer(theId);
//	    theModel.addAttribute("customer", theCustomer);
//	    return "registration-form-customer";
//    }

//    @GetMapping("/deleteCustomer")
//    public String deleteCustomer(@RequestParam("customerId") int theId) {
//	    customerService.deleteCustomer(theId);
//	    return "redirect:/shop";
//    }

//    @GetMapping("/payment")
//    public String getPayments(Model model){
//        List<Payment> payments2 = paymentService.getPayments();
//        model.addAttribute("payments2", payments2);
//        return "management-panel";
//    }
}