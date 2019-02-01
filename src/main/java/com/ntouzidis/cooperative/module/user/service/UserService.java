package com.ntouzidis.cooperative.module.user.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.entity.Wallet;
import com.ntouzidis.cooperative.module.user.repository.CustomerToTraderLinkRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> findCustomer(Integer id) {
        return userRepository.findById(id).filter(this::isCustomer);
    }

    public Optional<User> findCustomer(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).filter(this::isCustomer);
    }

    public Optional<User> findTrader(Integer id) {
        return userRepository.findById(id).filter(this::isTrader);
    }

    public Optional<User> findTrader(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).filter(this::isTrader);
    }

    public Optional<User> getPersonalTrader(String username) {
        User user = findByUsername(username).orElseThrow(() -> new RuntimeException("customer not found"));
        return Optional.ofNullable(customerToTraderLinkRepository.findByCustomer(user)).map(CustomerToTraderLink::getTrader);
    }

    @Transactional
    public List<User> getFollowers(User trader) {
        return customerToTraderLinkRepository.findAllByTrader(trader).stream().map(CustomerToTraderLink::getCustomer).collect(Collectors.toList());
    }

    public List<User> getEnabledFollowers(User trader) {
        return getFollowers(trader).stream().filter(User::getEnabled).collect(Collectors.toList());
    }

    public List<User> getTraders() {
        return  userRepository.findAll().stream().filter(authorityService::isTrader).collect(Collectors.toList());
    }

    public Set<GrantedAuthority> getUserAuthorities(String username) {
        return authorityService.getAuthorities(username);
    }

    public boolean isCustomer(User user) {
        return authorityService.isCustomer(user);
    }

    public boolean isTrader(User user) {
        return authorityService.isTrader(user);
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void linkTrader(User user, int traderId) {
        findTrader(traderId).ifPresent(i -> {
            Preconditions.checkArgument(Objects.nonNull(user), "User cannot be null");

            CustomerToTraderLink link = new CustomerToTraderLink();
            link.setCustomer(user);
            link.setTrader(i);
            link.setCreate_date();

            customerToTraderLinkRepository.save(link);
            user.setEnabled(false);
            userRepository.save(user);
        });
    }

    @Transactional
    public void unlinkTrader(User user) {
        CustomerToTraderLink link = customerToTraderLinkRepository.findByCustomer(user);
        customerToTraderLinkRepository.delete(link);
    }

    @Transactional
    public User setFixedQty(User user, String symbol, Long qty) {
        Preconditions.checkArgument(qty != null, "Qty cannot be null");

        if (symbol.equals(Symbol.XBTUSD.getValue()))
            user.setFixedQtyXBTUSD(qty);
        else if (symbol.equals(Symbol.ETHUSD.getValue()))
            user.setFixedQtyETHUSD(qty);
        else if (symbol.equals(Symbol.ADAXXX.getValue()))
            user.setFixedQtyADAZ18(qty);
        else if (symbol.equals(Symbol.BCHXXX.getValue()))
            user.setFixedQtyBCHZ18(qty);
        else if (symbol.equals(Symbol.EOSXXX.getValue()))
            user.setFixedQtyEOSZ18(qty);
        else if (symbol.equals(Symbol.ETHXXX.getValue()))
            user.setFixedQtyXBTJPY(qty); // TODO replace with ETHH19
        else if (symbol.equals(Symbol.LTCXXX.getValue()))
            user.setFixedQtyLTCZ18(qty);
        else if (symbol.equals(Symbol.TRXXXX.getValue()))
            user.setFixedQtyTRXZ18(qty);
        else if (symbol.equals(Symbol.XRPXXX.getValue()))
            user.setFixedQtyXRPZ18(qty);
        else
            throw new IllegalArgumentException("Couldn't set the qty");

        return userRepository.saveAndFlush(user);
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
    public User saveKeys(User user, String apiKey, String apiSecret) {
        if (apiKey != null) user.setApiKey(apiKey);
        if (apiSecret != null) user.setApiSecret(apiSecret);

        return userRepository.save(user);
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
        user.setFixedQtyXBTUSD(0L);
        user.setFixedQtyXBTJPY(0L);
        user.setFixedQtyADAZ18(0L);
        user.setFixedQtyBCHZ18(0L);
        user.setFixedQtyEOSZ18(0L);
        user.setFixedQtyETHUSD(0L);
        user.setFixedQtyLTCZ18(0L);
        user.setFixedQtyTRXZ18(0L);
        user.setFixedQtyXRPZ18(0L);
        user.setFixedQtyXBTKRW(0L);

        userRepository.save(user);

        authorityService.createAuthorities(user.getUsername(), authorities);

        return userDetails;
    }

    private boolean usernameExists(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).isPresent();
    }
}
