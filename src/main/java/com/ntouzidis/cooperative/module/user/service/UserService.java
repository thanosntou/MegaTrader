package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.entity.Wallet;
import com.ntouzidis.cooperative.module.user.repository.CustomerToTraderLinkRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final CustomerToTraderLinkRepository customerToTraderLinkRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthorityService authorityService,
                       CustomerToTraderLinkRepository customerToTraderLinkRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
        this.customerToTraderLinkRepository = customerToTraderLinkRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> findCustomer(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).filter(this::isCustomer);
    }

    public Optional<User> findTrader(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).filter(this::isTrader);
    }

    public Optional<User> getPersonalTrader(String username) {
        User user = findByUsername(username).orElseThrow(() -> new RuntimeException("customer not found"));
        return Optional.ofNullable(customerToTraderLinkRepository.findByCustomer(user)).map(CustomerToTraderLink::getTrader);
    }

    public List<CustomerToTraderLink> getPersonalCustomers(String traderUsername) {
        User trader = findTrader(traderUsername).orElseThrow(() -> new RuntimeException("trader not found"));
        return customerToTraderLinkRepository.findAllByTrader(trader);
    }

    public List<User> getTraders() {
        return  userRepository.findAll().stream().filter(authorityService::isTrader).collect(Collectors.toList());
    }

    public Set<GrantedAuthority> getUserAuthorities(String username) {
        return authorityService.getAuthorities(username);
    }

    public void linkTrader(User user, int traderId) {
        CustomerToTraderLink link = new CustomerToTraderLink();
        link.setCustomer(user);
        link.setTrader(findTrader(userRepository.findById(traderId).orElseThrow(RuntimeException::new).getUsername()).orElse(null));
//        link.setTrader(userRepository.findById(traderId).orElseThrow(() -> new RuntimeException("trader not found")));
        link.setCreate_date();

        customerToTraderLinkRepository.save(link);
    }

    public void unlinkTrader(User user) {
        CustomerToTraderLink link = customerToTraderLinkRepository.findByCustomer(user);

        customerToTraderLinkRepository.delete(link);
    }

    public void setFixedQty(String username, Long qty) {
        User user = userRepository.findByUsername(username);
        user.setFixedQty(qty);
        userRepository.save(user);
    }

    public boolean isCustomer(User user) {
        return authorityService.isCustomer(user);
    }

    public boolean isTrader(User user) {
        return authorityService.isTrader(user);
    }

    @Transactional
    public User createCustomer(User user, String password) {
        return createUSer(user, password, AuthorityUtils.createAuthorityList("ROLE_CUSTOMER"));
    }

    @Transactional
    public User createTrader(User user, String password) {
        return createUSer(user, password, AuthorityUtils.createAuthorityList("ROLE_TRADER"));
    }

    @Transactional
    public void saveKeys(String username, String apiKey, String apiSecret) {
        User principal = userRepository.findByUsername(username);
        if (apiKey != null) principal.setApiKey(apiKey);
        if (apiSecret != null) principal.setApiSecret(apiSecret);

        userRepository.save(principal);
    }

    @Transactional(readOnly=true)
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(new CustomUserDetails(userRepository.findByUsername(username), authorityService.getAuthorities(username)))
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private User createUSer(User userDetails, String password, List<GrantedAuthority> authorities) {
        boolean con = usernameExists(userDetails.getUsername());
        Preconditions.checkArgument(!con, "username exists");

        User user = new User(userDetails.getUsername(), passwordEncoder.encode(password));

        Wallet wallet = new Wallet();
        wallet.setBalance(0L);

        user.setEmail(userDetails.getEmail());
        user.setCreate_date();
        user.setWallet(wallet);

        userRepository.save(user);

        authorityService.createAuthorities(user.getUsername(), authorities);

        return userDetails;
    }

    private boolean usernameExists(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).isPresent();
    }
}
