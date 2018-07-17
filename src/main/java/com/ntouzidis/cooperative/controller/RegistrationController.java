package com.ntouzidis.cooperative.controller;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import com.ntouzidis.cooperative.module.Customer.Customer;
import com.ntouzidis.cooperative.module.Customer.CustomerService;
import com.ntouzidis.cooperative.module.Member.Member;
import com.ntouzidis.cooperative.module.Member.MemberService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private final static org.slf4j.Logger logger =  
            LoggerFactory.getLogger(RegistrationController.class);
    
    @Autowired
    private UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MemberService memberService;
    
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
            theModel.addAttribute("customer", new Customer());
            return "registration-form-customer";
        }else if (option.equals("member")){
            theModel.addAttribute("member", new Member());
            return "registration-form-member";
        }
	return "access-denied";	
    }

    @PostMapping("/customerRegistrationForm")
    public String processRegistrationForm(
				@Valid @ModelAttribute("customer") Customer theCustomer,
				BindingResult theBindingResult,
				@RequestParam(required=false,name="pass") String pass,
                @RequestParam(required=false,name="confirmPass") String confirmPass,
				Model theModel,Principal principal) {

        if (theBindingResult.hasErrors() || pass == null || pass.length() < 5 || !pass.equals(confirmPass)) {
            theModel.addAttribute("lathos", "Invalid password");
            return "registration-form-customer";
        }

        //TODO: don't remember the purpose of this if (maybe to change a registered customer his pass?)
        if (theCustomer.getId() != null && pass.equals(confirmPass)) {
            if (principal instanceof UserDetails) {
                changeePassword(principal, pass);
            }
            customerService.save(theCustomer);
            return "redirect:/management-panel";
        }

        // check the database if user already exists
        if (doesUserExist(theCustomer.getUsername())) {
            theModel.addAttribute("registrationError", "User name already exists.");
            return "registration-form-customer";
        }

        //	 encrypt the password
        String encodedPassword = passwordEncoder.encode(pass);
        encodedPassword = "{bcrypt}" + encodedPassword;
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_CUSTOMER");
        User tempUser = new User(theCustomer.getUsername(), encodedPassword, authorities);
        userDetailsManager.createUser(tempUser);
        customerService.save(theCustomer);

        return "redirect:/";
    }

    @PostMapping("/memberRegistrationForm")
    public String memberRegistrationForm(
				@Valid @ModelAttribute("member") Member member,
                                BindingResult theBindingResult,
                                @RequestParam(required=false,name="pass") String pass,
                                @RequestParam(required=false,name="confirmPass") String confirmPass,
				Model theModel,
                                Principal principal) {

        if (theBindingResult.hasErrors() || pass == null || pass.length() < 5 || !pass.equals(confirmPass)){
            theModel.addAttribute("lathos", "Invalid password");
            return "registration-form-member";
        }

        if (member.getId() != null && pass.equals(confirmPass)){
            if (principal instanceof UserDetails) {
                changeePassword(principal, pass);
            }
            memberService.save(member);
            return "redirect:/management-panel";
        }

	    if (doesUserExist(member.getUsername())) {
            theModel.addAttribute("registrationError", "User name already exists.");
            return "registration-form-member";
        }

	    // encrypt the password
        String encodedPassword = passwordEncoder.encode(pass);
        encodedPassword = "{bcrypt}" + encodedPassword;
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_MEMBER");
        User tempUser = new User(member.getUsername(), encodedPassword, authorities);
        userDetailsManager.createUser(tempUser);
        memberService.save(member);

        return "redirect:/welcome";
    }

    private void changeePassword(Principal principal, String pass) {
        String username = ((UserDetails) principal).getUsername();
        String password = ((UserDetails) principal).getPassword();
        String encodedPassword = passwordEncoder.encode(pass);
        encodedPassword = "{bcrypt}" + encodedPassword;
        userDetailsManager.changePassword(password, encodedPassword);
    }
    
    @GetMapping("/memberUpdateForm")
    public String memberUpdate(@RequestParam("memberId") int id, Model theModel) {
	    Member member = memberService.get(id);
	    theModel.addAttribute("member", member);

	    return "registration-form-member";
    }
    
    
	
    private boolean doesUserExist(String userName) {
	    // check the database if the user already exists
	    return userDetailsManager.userExists(userName);
    }

}
