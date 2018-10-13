package com.ntouzidis.cooperative.module.common.event;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import com.ntouzidis.cooperative.module.user.entity.Login;
import com.ntouzidis.cooperative.module.user.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        if (event != null) {
            CustomUserDetails userDetails;

            Authentication authentication = event.getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                userDetails = (CustomUserDetails) authentication.getPrincipal();

                Login login = new Login();
                login.setUser(userDetails.getUser());
                login.setCreate_date();

                loginRepository.save(login);
            }

        }
    }
}
