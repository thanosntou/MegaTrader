package com.ntouzidis.cooperative.module.user;

import com.ntouzidis.cooperative.module.user.service.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private IUserService userService;

    public RegistrationController() {
    }
    
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
	StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
	dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    
    @GetMapping("/showRegistrationFormOption")
    public String showMyLoginOptionsPage(Model theModel) {
	return "register-option";	
    }
    
    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(@RequestParam(required=false,name="selector") String option, Model theModel) {
        if (option == null){
            return "redirect:/register/showRegistrationFormOption";}
        if (option.equals("customer")){
//            theModel.addAttribute("customer", new Customer());
            return "registration-form-customer";
        }else if (option.equals("member")){

            return "registration-form-member";
        }
	return "access-denied";	
    }

    @PostMapping("/customerRegistrationForm")
    public String processRegistrationForm(
//				@Valid @ModelAttribute("customer") Customer theCustomer,
				BindingResult theBindingResult,
				@RequestParam(required=false,name="pass") String pass,
                @RequestParam(required=false,name="confirmPass") String confirmPass,
				Model theModel,Principal principal) {

        if (theBindingResult.hasErrors() || pass == null || pass.length() < 5 || !pass.equals(confirmPass)) {
            theModel.addAttribute("lathos", "Invalid password");
            return "registration-form-customer";
        }

//        //TODO: don't remember the purpose of this if (maybe to change a registered customer his pass?)
//        if (theCustomer.getId() != null && pass.equals(confirmPass)) {
//            if (principal instanceof UserDetails) {
//                changePassword(principal, pass);
//            }
//            customerService.save(theCustomer);
//            return "redirect:/management-panel";
//        }

        // check the database if user already exists
//        if (doesUserExist(theCustomer.getUsername())) {
//            theModel.addAttribute("registrationError", "user name already exists.");
//            return "registration-form-customer";
//        }
//
//        userService.createCustomer(theCustomer.getUsername(), pass);

        return "redirect:/";
    }

//    @PostMapping("/memberRegistrationForm")
//    public String memberRegistrationForm(
//				@Valid @ModelAttribute("member") Member member
//                                BindingResult theBindingResult,
//                                @RequestParam(required=false,name="pass") String pass,
//                                @RequestParam(required=false,name="confirmPass") String confirmPass,
//				Model theModel,
//                                Principal principal) {
//
//        if (theBindingResult.hasErrors() || pass == null || pass.length() < 5 || !pass.equals(confirmPass)){
//            theModel.addAttribute("lathos", "Invalid password");
//            return "registration-form-member";
//        }
//
////        if (member.getId() != null && pass.equals(confirmPass)){
////            if (principal instanceof UserDetails) {
////                changePassword(principal, pass);
////            }
////            memberService.save(member);
////            return "redirect:/management-panel";
////        }
//
//	    if (doesUserExist(member.getUsername())) {
//            theModel.addAttribute("registrationError", "user name already exists.");
//            return "registration-form-member";
//        }
//        userService.createTrader(member.getUsername(), pass);
//
//        return "redirect:/";
//    }

    private void changePassword(Principal principal, String pass) {
        String username = ((UserDetails) principal).getUsername();
        String password = ((UserDetails) principal).getPassword();
        String encodedPassword = passwordEncoder.encode(pass);
        encodedPassword = "{bcrypt}" + encodedPassword;
        userDetailsManager.changePassword(password, encodedPassword);
    }
    
    
	
    private boolean doesUserExist(String userName) {
	    // check the database if the user already exists
	    return userDetailsManager.userExists(userName);
    }

}
