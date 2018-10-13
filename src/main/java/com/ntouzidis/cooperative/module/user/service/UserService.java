package com.ntouzidis.cooperative.module.user.service;

import com.ntouzidis.cooperative.module.user.entity.*;
import com.ntouzidis.cooperative.module.user.repository.AuthorityRepository;
import com.ntouzidis.cooperative.module.user.repository.CustomerToTraderLinkRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthorityService authorityService;
    private final CustomerToTraderLinkRepository customerToTraderLinkRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       AuthorityService authorityService, CustomerToTraderLinkRepository customerToTraderLinkRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.authorityService = authorityService;
        this.customerToTraderLinkRepository = customerToTraderLinkRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.of(userRepository.findByUsername(username));
    }

    public List<User> getTraders() {
        return userRepository.findAll().stream()
                .filter(i -> authorityService.findAuthority(i.getUsername()).getAuthority().equals("ROLE_TRADER)"))
                .collect(Collectors.toList());
    }

    public User getPersonalTrader(String username) {
        User user = findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));

        CustomerToTraderLink link = customerToTraderLinkRepository.findByCustomer(user);

        if (link !=null)
            return link.getTrader();

        return null;
    }

    public void linkTrader(User user, int traderId) {
        CustomerToTraderLink link = new CustomerToTraderLink();
        link.setCustomer(user);
        link.setTrader(userRepository.findById(traderId).orElseThrow(RuntimeException::new));
        link.setCreate_date();

        customerToTraderLinkRepository.save(link);
    }

    public void unlinkTrader(User user, int traderId) {
        CustomerToTraderLink link = customerToTraderLinkRepository.findByCustomer(user);

        customerToTraderLinkRepository.delete(link);
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
        User domainUser = userRepository.findByUsername(username);

        Set<GrantedAuthority> authorities = new HashSet<>();

        authorityRepository.findAuthoritiesByUsername(username)
                .stream()
                .map(Authority::getAuthority)
                .forEach(i -> authorities.add(new SimpleGrantedAuthority(i)));

        return new CustomUserDetails(domainUser, authorities);
    }

    private User createUSer(User userDetails, String password, List<GrantedAuthority> authorities) {
        boolean con = usernameExists(userDetails.getUsername());
        Preconditions.checkArgument(!con, "username exists");

        User user = new User(userDetails.getUsername(), passwordEncoder.encode(password), authorities);

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
