package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.entity.Wallet;
import com.ntouzidis.cooperative.module.user.repository.CustomerToTraderLinkRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import com.ntouzidis.cooperative.module.user.repository.WalletRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;
    private final CustomerToTraderLinkRepository customerToTraderLinkRepository;
    private final WalletRepository walletRepository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserService(UserDetailsManager userDetailsManager, UserRepository userRepository, CustomerToTraderLinkRepository customerToTraderLinkRepository, AuthorityService authorityService, WalletRepository walletRepository) {
        this.userDetailsManager = userDetailsManager;
        this.userRepository = userRepository;
        this.customerToTraderLinkRepository = customerToTraderLinkRepository;
        this.walletRepository = walletRepository;
        this.authorityService = authorityService;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.of(userRepository.findByUsername(username));
    }

    public List<User> getTraders() {

        List<User> users =  userRepository.findAll();


        List<User> traders = users.stream()
                .filter(i -> authorityService.findAuthorities(i.getUsername()).getAuthority().equals("ROLE_TRADER"))
                .collect(Collectors.toList());

        return traders;
    }

    @Override
    public User createCustomer(User user, String password) {
        return createUSer(user, password, AuthorityUtils.createAuthorityList("ROLE_CUSTOMER"));
    }

    @Override
    public User createTrader(User user, String password) {
        return createUSer(user, password, AuthorityUtils.createAuthorityList("ROLE_TRADER"));
    }

    public User getPersonalTrader(String username) {
        User user = findByUsername(username).orElseThrow(() -> new RuntimeException("No user found with username: " + username));

        CustomerToTraderLink link = customerToTraderLinkRepository.findByCustomer(user);

        if (link != null)
            return link.getTrader();

        return null;
    }

    @Override
    public void linkTrader(User user, int traderId) {
        CustomerToTraderLink link = new CustomerToTraderLink();
        link.setCustomer(user);
        link.setTrader(userRepository.findById(traderId).orElseThrow(RuntimeException::new));
        link.setCreate_date();

        customerToTraderLinkRepository.save(link);
    }

    @Override
    public void unlinkTrader(User user, int traderId) {
        CustomerToTraderLink link = customerToTraderLinkRepository.findByCustomer(user);

        customerToTraderLinkRepository.delete(link);
    }



    @Transactional
    public void saveKeys(String username, String apiKey, String apiSecret) {
        User principal = userRepository.findByUsername(username);
        if (apiKey != null) principal.setApiKey(apiKey);
        if (apiSecret != null) principal.setApiSecret(apiSecret);

        userRepository.save(principal);
    }



    private User createUSer(User user, String password, List<GrantedAuthority> authorities) {
        User userDetails = new User(user.getUsername(), "{bcrypt}"+passwordEncoder.encode(password), authorities);

        Wallet wallet = new Wallet();
        wallet.setBalance(0L);

        userDetails.setEmail(user.getEmail());
        userDetails.setCreate_date();
        userDetails.setWallet(wallet);

        if (!userDetailsManager.userExists(userDetails.getUsername())) {
            userRepository.saveAndFlush(userDetails);
            authorityService.createAuthorities(userDetails.getUsername(), authorities);
        }

        return userDetails;
    }



}
