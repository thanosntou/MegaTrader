package com.ntouzidis.cooperative.UnitTests;

import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.repository.UserRepository;
import com.ntouzidis.cooperative.module.user.service.AuthorityService;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.acls.model.NotFoundException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorityService authorityService;

    @Test
    public void findByUsername_shouldWork() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findOne("testUser")).thenReturn(Optional.of(user));

        User foundUser = userService.getOne("testUser");

        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    public void findCustomer_shouldWork() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findOne("testUser")).thenReturn(Optional.of(user));
        when(authorityService.isCustomer(user)).thenReturn(true);

        User foundUser = userService.findCustomer("testUser")
                .orElseThrow(() -> new NotFoundException("BitmexUser not found"));

        assertEquals(foundUser.getUsername(), user.getUsername());
    }

    @Test
    public void findTrader_shouldWork() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findOne("testUser")).thenReturn(Optional.of(user));
        when(authorityService.isTrader(user)).thenReturn(true);

        User foundUser = userService.findTrader("testUser")
                .orElseThrow(() -> new NotFoundException("BitmexUser not found"));

        assertEquals(foundUser.getUsername(), user.getUsername());
    }
}
