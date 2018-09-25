package com.ntouzidis.cooperative.module.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService(UserDetailsManager userDetailsManager, UserRepository userRepository, AuthorityRepository authorityRepository, AuthorityService authorityRepository1, AuthorityService authorityService) {
        this.userDetailsManager = userDetailsManager;
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User createCustomer(String username, String password) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_CUSTOMER");
        String encodedPassword = passwordEncoder.encode(password);

        return createUSer(username, encodedPassword, authorities);
    }

    @Transactional
    public User createTrader(String username, String password) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_TRADER");
        String encodedPassword = passwordEncoder.encode(password);

        return createUSer(username, encodedPassword, authorities);
    }

    @Transactional
    void saveKeys(String username, String apiKey, String apiSecret) {
        User principal = userRepository.findByUsername(username);
        if (apiKey != null) principal.setApiKey(apiKey);
        if (apiSecret != null) principal.setApiSecret(apiSecret);

        userRepository.save(principal);
    }


    private User createUSer(String username, String encodedPassword, List<GrantedAuthority> authorities) {
        User user = new User(username, encodedPassword, authorities);
        user.setCreate_date(LocalDate.now());

        if (!userDetailsManager.userExists(username)) {
            userRepository.save(user);
            authorityService.createAuthorities(username, authorities);
        }

        return user;
    }

}
