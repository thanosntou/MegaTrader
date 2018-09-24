package com.ntouzidis.cooperative.module.user;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserDetailsManager userDetailsManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


//    @Transactional
//    public User create() {
//        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
//        String encodedPassword = passwordEncoder.encode("kobines");
//        User user = new User("enso", encodedPassword, authorities);
//        user.setCreate_date(LocalDate.now());
////        ((User)user).setCreate_day(LocalDate.now());
//        if (!userDetailsManager.userExists(user.getUsername()))
////            userDetailsManager.createUser(user);
//            userRepository.save(user);
//
//        return user;
//    }

    public void saveKeys(String username, String apiKey, String apiSecret) {
        User principal = userRepository.findByUsername(username);
        principal.setApiKey(apiKey);
        principal.setApiSecret(apiSecret);
        userRepository.save(principal);
    }

}
