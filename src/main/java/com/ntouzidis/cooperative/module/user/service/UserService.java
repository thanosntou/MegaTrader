package com.ntouzidis.cooperative.module.user.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.NotFoundException;
import com.ntouzidis.cooperative.module.common.enumeration.Client;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.service.SimpleEncryptor;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.user.entity.*;
import com.ntouzidis.cooperative.module.user.repository.CustomerToTraderLinkRepository;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import com.ntouzidis.cooperative.module.user.repository.TenantRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private final Context context;
  private final TenantRepository tenantRepository;
  private final BitmexService bitmexService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SimpleEncryptor simpleEncryptor;
  private final LoginRepository loginRepository;
  private final AuthorityService authorityService;
  private final CustomerToTraderLinkRepository customerToTraderLinkRepository;

  public UserService(Context context, TenantRepository tenantRepository, UserRepository userRepository, AuthorityService authorityService,
                     CustomerToTraderLinkRepository customerToTraderLinkRepository, PasswordEncoder passwordEncoder,
                     SimpleEncryptor simpleEncryptor, LoginRepository loginRepository, BitmexService bitmexService) {
    this.context = context;
    this.tenantRepository = tenantRepository;

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

  public User getOne(Integer id, String username) {
    Preconditions.checkArgument(Objects.nonNull(id) || StringUtils.isNotBlank(username));

    if (Objects.nonNull(id))
      return getOne(id);
    else
      return getOne(username);
  }

  public User getOne(int id) {
    return userRepository.findOne(id).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, id)));
  }

  public User getOne(String username) {
    return userRepository.findOne(username).orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND, username)));
  }

  public User getTrader(String username) {
    return userRepository.findOne(username).filter(this::isTrader)
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

  public List<User> getAll() {
    return userRepository.findAll();
  }

//  public Optional<User> findById(Integer id) {
//    return userRepository.findOne(id);
//  }
//
//  public Optional<User> findByUsername(String username) {
//    return userRepository.findOne(username);
//  }

  public Optional<User> findCustomer(Integer id) {
    return userRepository.findOne(id).filter(this::isCustomer);
  }

  public Optional<User> findCustomer(String username) {
    return userRepository.findOne(username)
        .filter(this::isCustomer);
  }

  private Optional<User> findTrader(Integer id) {
    return userRepository.findOne(id).filter(this::isTrader);
  }

  public Optional<User> findTrader(String username) {
    return userRepository.findOne(username).filter(this::isTrader);
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
    userRepository.update(user);
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
      userRepository.update(customer);
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

    userRepository.update(user);
    return user;
  }

  @Transactional
  public User saveKeys(User user, String apiKey, String apiSecret) {
    if (apiKey != null) user.setApiKey(simpleEncryptor.encrypt(apiKey));
    if (apiSecret != null) user.setApiSecret(simpleEncryptor.encrypt(apiSecret));

    userRepository.update(user);
    return user;
  }

  @Transactional
  public User updateClient(User user, Client client) {
    if (client != null) user.setClient(client);

    userRepository.update(user);
    return user;
  }

  @Transactional
  public User changePassword(int id, String newPass, String confirmPass) {
    Preconditions.checkArgument( newPass != null && confirmPass != null, "Password is empty");
    Preconditions.checkArgument(newPass.equals(confirmPass), "Wrong confirmation password");

    Optional<User> userOpt = userRepository.findOne(id);

    if (userOpt.isPresent()) {
      userOpt.get().setPassword(passwordEncoder.encode(newPass));
      userRepository.update(userOpt.get());
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

  @Transactional
  public User createCustomer(String username, String email, String pass) {
    return createUser(username, pass, email, context.getTenant(), AuthorityUtils.createAuthorityList("ROLE_CUSTOMER"));
  }

  @Transactional
  public User createTrader(String username, String email, String pass) {
    return createUser(username, pass, email, context.getTenant(), AuthorityUtils.createAuthorityList("ROLE_TRADER"));
  }

  @Transactional
  public User createAdmin(Tenant tenant, String username, String email, String pass) {
    return createUser(username, pass, email, tenant, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
  }

  private User createUser(String username, String pass, String email, Tenant tenant,  List<GrantedAuthority> authorities) {
    Preconditions.checkArgument(!usernameExists(username), "username exists");

    User user = new User(username, passwordEncoder.encode(pass));

    Wallet wallet = new Wallet();
    wallet.setBalance(0L);

    user.setTenant(tenant);
    user.setEmail(email);
    user.setWallet(wallet);
    user.setClient(Client.BITMEX);
    user.setEnabled(false);
    user.setFixedQtyXBTUSD(0);
    user.setFixedQtyETHUSD(0);
    user.setFixedQtyADAZ18(0);
    user.setFixedQtyXBTJPY(0);
    user.setFixedQtyBCHZ18(0);
    user.setFixedQtyEOSZ18(0);
    user.setFixedQtyLTCZ18(0);
    user.setFixedQtyTRXZ18(0);
    user.setFixedQtyXRPZ18(0);
    user.setFixedQtyXBTKRW(0);
    user.setApiKey("Fill_Me");
    user.setApiSecret("Fill_Me");

    userRepository.save(user);
    authorityService.createAuthorities(user.getUsername(), authorities);
    return user;
  }

  private boolean usernameExists(String username) {
    return userRepository.findOneGlobal(username).isPresent();
  }

  @Transactional(readOnly=true)
  @Override
  public CustomUserDetails loadUserByUsername(String username) {
    return new CustomUserDetails(
        userRepository.findOneGlobal(username).orElseThrow(NotFoundException::new),
        authorityService.getAuthorities(username)
    );
  }
}
