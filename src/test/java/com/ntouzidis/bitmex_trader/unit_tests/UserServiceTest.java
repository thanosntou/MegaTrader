package com.ntouzidis.bitmex_trader.unit_tests;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.comparator.BooleanComparator;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

//  @InjectMocks
//  private UserService userService;
//
//  @Mock
//  private UserRepository userRepository;
//
//  @Test
//  public void findByUsername_shouldWork() {
//    User user = new User();
//    user.setUsername("testUser");
//
//    when(userRepository.findOne("testUser")).thenReturn(Optional.of(user));
//
//    User foundUser = userService.getOne("testUser");
//
//    assertEquals(foundUser.getUsername(), user.getUsername());
//  }
//
//  @Test
//  public void findCustomer_shouldWork() {
//    User user = new User();
//    user.setUsername("testUser");
//
//    when(userRepository.findOne("testUser")).thenReturn(Optional.of(user));
//    when(UserUtils.isFollower(user)).thenReturn(true);
//
//    // todo fix
//    User foundUser = userService.getOne("testUser");
//
//    assertEquals(foundUser.getUsername(), user.getUsername());
//  }
//
//  @Test
//  public void findTrader_shouldWork() {
//    User user = new User();
//    user.setUsername("testUser");
//
//    when(userRepository.findOne("testUser")).thenReturn(Optional.of(user));
//    when(UserUtils.isTrader(user)).thenReturn(true);
//
//    // todo fix
//    User foundUser = userService.getOne("testUser");
//
//    assertEquals(foundUser.getUsername(), user.getUsername());
//  }

}
