package com.ntouzidis.cooperative.module.user.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.enumeration.Client;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.CustomerToTraderLink;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.entity.Wallet;
import com.ntouzidis.cooperative.module.user.repository.CustomerToTraderLinkRepository;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

  private Logger logger = LoggerFactory.getLogger(UserService.class);
  private static final String USER_NOT_FOUND = "User %s not found";

  private final BitmexService bitmexService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SimpleEncryptor simpleEncryptor;
  private final LoginRepository loginRepository;
  private final AuthorityService authorityService;
  private final CustomerToTraderLinkRepository customerToTraderLinkRepository;

  public UserService(UserRepository userRepository, AuthorityService authorityService,
                     CustomerToTraderLinkRepository customerToTraderLinkRepository, PasswordEncoder passwordEncoder,
                     SimpleEncryptor simpleEncryptor, LoginRepository loginRepository, BitmexService bitmexService) {

    this.bitmexService = bitmexService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.simpleEncryptor = simpleEncryptor;
    this.loginRepository = loginRepository;
    this.authorityService = authorityService;
    this.customerToTraderLinkRepository = customerToTraderLinkRepository;

  }

  public List<User> getFollowers(User trader) {
    return customerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .map(CustomerToTraderLink::getCustomer)
        .collect(Collectors.toList());
  }

  public List<User> getEnabledFollowers(User trader) {
    return customerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .map(CustomerToTraderLink::getCustomer)
        .filter(User::getEnabled)
        .collect(Collectors.toList());
  }

  public List<User> getNonHiddenFollowers(User trader) {
    return customerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .filter(link -> !link.isHidden())
        .map(CustomerToTraderLink::getCustomer)
        .collect(Collectors.toList());
  }

  private List<User> getEnabledAndNonHiddenFollowers(User trader) {
    return customerToTraderLinkRepository.findAllByTrader(trader)
        .stream()
        .filter(link -> !link.isHidden())
        .map(CustomerToTraderLink::getCustomer)
        .filter(User::getEnabled)
        .collect(Collectors.toList());
  }

  public User getGuideFollower(User trader) {
    return customerToTraderLinkRepository.findAllByTraderAndGuide(trader, true)
        .stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Guide follower not found"))
        .getCustomer();
  }

  public List<User> getTraders() {
    return userRepository.findAll()
        .stream()
        .filter(authorityService::isTrader)
        .collect(Collectors.toList());
  }

  public User getOne(int id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND, id)));
  }

  public User getOne(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND, username)));
  }

  public User getTrader(String username) {
    return userRepository.findByUsername(username).filter(this::isTrader)
        .orElseThrow(() -> new RuntimeException("Trader " + username + "not found"));
  }

  public User getTrader(Integer id) {
    return findTrader(id).orElseThrow(() -> new RuntimeException("Trader not found"));
  }

  public User getPersonalTraderOf(User customer) {
    return customerToTraderLinkRepository.findByCustomer(customer)
        .map(CustomerToTraderLink::getTrader)
        .orElseThrow(() -> new RuntimeException("User don't have a personal trader"));
  }

  public List<User> findAll() {
    return userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
  }

  public Optional<User> findById(Integer id) {
    return userRepository.findById(id);
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> findCustomer(Integer id) {
    return userRepository.findById(id).filter(this::isCustomer);
  }

  public Optional<User> findCustomer(String username) {
    return userRepository.findByUsername(username)
        .filter(this::isCustomer);
  }

  private Optional<User> findTrader(Integer id) {
    return userRepository.findById(id).filter(this::isTrader);
  }

  public Optional<User> findTrader(String username) {
    return userRepository.findByUsername(username).filter(this::isTrader);
  }

  public Set<GrantedAuthority> getUserAuthorities(String username) {
    return authorityService.getAuthorities(username);
  }

  private boolean isCustomer(User user) {
    return authorityService.isCustomer(user);
  }

  private boolean isTrader(User user) {
    return authorityService.isTrader(user);
  }

  public boolean isAdmin(User user) {
    return authorityService.isAdmin(user);
  }

  @Transactional
  public User update(User user) {
    userRepository.save(user);
    return user;
  }

  public double calculateTotalVolume(User trader) {
    int sum = getEnabledAndNonHiddenFollowers(trader).stream().mapToInt(user -> {
      try {
        return ((Integer) bitmexService.getUserMargin(user).get("walletBalance"));
      }
      catch (Exception ignored) {
      }
      return 0;
    })
            .sum();

    return (double) sum / 100000000;
  }

  public double calculateActiveVolume(User trader) {
    return getEnabledAndNonHiddenFollowers(trader).stream().mapToDouble(user -> {
      try {
        double userBalance = ((Integer) bitmexService.getUserMargin(user).get("walletBalance")).doubleValue();
        double userPercentage = (double) user.getFixedQtyXBTUSD() / (double) 100;
        return userBalance * userPercentage / 100000000;
      }
      catch (Exception e) {
        logger.warn(String.format("failed to get read balance of follower: %s", user.getUsername()));
      }
      return 0;
    })
            .sum();
  }

  public Map<String, Double> getFollowerBalances(User trader) {
    Map<String, Double> map = new HashMap<>();

    getEnabledFollowers(trader).forEach(user -> {
      try {
        double userBalance = ((Integer) bitmexService.getUserMargin(user).get("walletBalance")).doubleValue() / 100000000;
        map.put(user.getUsername(), userBalance);
      } catch (Exception e) {
        logger.warn(String.format("failed to get read balance of follower: %s", user.getUsername()));
      }
    });
    return map;
  }

  @Transactional
  public void linkTraderOf(User customer, User trader) {
      CustomerToTraderLink link = new CustomerToTraderLink();
      link.setCustomer(customer);
      link.setTrader(trader);
      link.setCreate_date(LocalDate.now());
      link.setGuide(false);
      link.setHidden(false);

      customerToTraderLinkRepository.save(link);
      customer.setEnabled(false);
      userRepository.save(customer);
  }

  @Transactional
  public void unlinkTraderOf(User user) {
    customerToTraderLinkRepository.findByCustomer(user).ifPresent(customerToTraderLinkRepository::delete);
  }

  @Transactional
  public void hideUser(User user) {
    customerToTraderLinkRepository.findByCustomer(user).ifPresent(i -> {
      i.setHidden(true);
      customerToTraderLinkRepository.save(i);
    });
  }

  @Transactional
  public User setFixedQty(User user, String symbol, long qty) {
    Preconditions.checkState(qty <= 100 && qty >= 0, "Wrong quantity input");

    if (symbol.equals(Symbol.XBTUSD.name()))
      user.setFixedQtyXBTUSD(qty);
    else if (symbol.equals(Symbol.ETHUSD.name()))
      user.setFixedQtyETHUSD(qty);
    else if (symbol.equals(Symbol.ADAM19.name()))
      user.setFixedQtyADAZ18(qty);
    else if (symbol.equals(Symbol.BCHM19.name()))
      user.setFixedQtyBCHZ18(qty);
    else if (symbol.equals(Symbol.EOSM19.name()))
      user.setFixedQtyEOSZ18(qty);
    else if (symbol.equals(Symbol.ETHM19.name()))
      user.setFixedQtyXBTJPY(qty);
    else if (symbol.equals(Symbol.LTCM19.name()))
      user.setFixedQtyLTCZ18(qty);
    else if (symbol.equals(Symbol.TRXM19.name()))
      user.setFixedQtyTRXZ18(qty);
    else if (symbol.equals(Symbol.XRPM19.name()))
      user.setFixedQtyXRPZ18(qty);
    else
      throw new IllegalArgumentException("Couldn't set the qty");

    userRepository.save(user);
    return user;
  }

  @Transactional
  public User createCustomer(String username, String email, String pass, String confirmPass) {
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setApiKey("");
    user.setApiSecret("");
    user.setEnabled(false);
    user.setFixedQtyXBTUSD(0L);
    user.setFixedQtyETHUSD(0L);
    user.setFixedQtyADAZ18(0L);
    user.setFixedQtyBCHZ18(0L);
    user.setFixedQtyEOSZ18(0L);
    user.setFixedQtyXBTJPY(0L);//TODO should change to ethxxx
    user.setFixedQtyLTCZ18(0L);
    user.setFixedQtyTRXZ18(0L);
    user.setFixedQtyXRPZ18(0L);

    return createUSer(user, pass, AuthorityUtils.createAuthorityList("ROLE_CUSTOMER"));
  }

  @Transactional
  public User createTrader(User user, String password) {
    return createUSer(user, password, AuthorityUtils.createAuthorityList("ROLE_TRADER"));
  }

  @Transactional
  public User saveKeys(User user, String apiKey, String apiSecret) {
    if (apiKey != null) user.setApiKey(simpleEncryptor.encrypt(apiKey));
    if (apiSecret != null) user.setApiSecret(simpleEncryptor.encrypt(apiSecret));

    userRepository.save(user);
    return user;
  }

  @Transactional
  public User updateClient(User user, Client client) {
    if (client != null) user.setClient(client);

    userRepository.save(user);
    return user;
  }

  @Transactional
  public User changePassword(int id, String newPass, String confirmPass) {
    Preconditions.checkArgument( newPass != null && confirmPass != null, "Password is empty");
    Preconditions.checkArgument(newPass.equals(confirmPass), "Wrong confirmation password");

    Optional<User> userOpt = findById(id);

    if (userOpt.isPresent()) {
      userOpt.get().setPassword(passwordEncoder.encode(newPass));
      userRepository.save(userOpt.get());
      return userOpt.get();
    }
    throw new RuntimeException("User not found");
  }

  @Transactional
  public void delete(User user) {
    loginRepository.deleteAllByUser(user);
    authorityService.deleteAuthorities(user);
    // better not able to delete if is linked
//        Optional.ofNullable(customerToTraderLinkRepository.findByCustomer(user)).ifPresent(
//                customerToTraderLinkRepository::delete
//        );
    userRepository.delete(user);
  }

  @Transactional(readOnly=true)
  @Override
  public CustomUserDetails loadUserByUsername(String username) {
    return new CustomUserDetails(getOne(username), authorityService.getAuthorities(username));
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
    user.setClient(Client.BITMEX);
    user.setEnabled(false);
    user.setFixedQtyXBTUSD(0);
    user.setFixedQtyXBTJPY(0);
    user.setFixedQtyADAZ18(0);
    user.setFixedQtyBCHZ18(0);
    user.setFixedQtyEOSZ18(0);
    user.setFixedQtyETHUSD(0);
    user.setFixedQtyLTCZ18(0);
    user.setFixedQtyTRXZ18(0);
    user.setFixedQtyXRPZ18(0);
    user.setFixedQtyXBTKRW(0);
    user.setApiKey(simpleEncryptor.encrypt("Fill_Me"));
    user.setApiSecret(simpleEncryptor.encrypt("Fill_Me"));

    userRepository.save(encodeUserApiKeys(user));
    authorityService.createAuthorities(user.getUsername(), authorities);
    return user;
  }

  private boolean usernameExists(String username) {
    return Optional.ofNullable(userRepository.findByUsername(username)).isPresent();
  }

  private User encodeUserApiKeys(User user) {
    if (user.getApiKey() != null)
      user.setApiKey(simpleEncryptor.encrypt(user.getApiKey()));
    if (user.getApiSecret() != null)
      user.setApiSecret(simpleEncryptor.encrypt(user.getApiSecret()));
    return user;
  }

  private User decodeUserApiKeys(User user) {
    if (user.getApiKey() != null)
      user.setApiKey(simpleEncryptor.decrypt(user.getApiKey()));
    if (user.getApiSecret() != null)
      user.setApiSecret(simpleEncryptor.decrypt(user.getApiSecret()));
    return user;
  }
}
