package com.ntouzidis.cooperative.UnitTests;

import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.AuthorityRepository;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import com.ntouzidis.cooperative.module.user.service.AuthorityService;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.acls.model.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityService authorityService;

    @Before
    public void setUp() {

    }

    @Test
    public void findByUsername_shouldWork() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User foundUser = userService.findByUsername("testUser")
                .orElseThrow(() -> new NotFoundException("BitmexUser not found"));

        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    public void findCustomer_shouldWork() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(authorityService.isCustomer(user)).thenReturn(true);

        User foundUser = userService.findCustomer("testUser")
                .orElseThrow(() -> new NotFoundException("BitmexUser not found"));

        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    public void findTrader_shouldWork() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(authorityService.isTrader(user)).thenReturn(true);

        User foundUser = userService.findTrader("testUser")
                .orElseThrow(() -> new NotFoundException("BitmexUser not found"));

        assertEquals(foundUser.getUsername(), user.getUsername());
    }
}
