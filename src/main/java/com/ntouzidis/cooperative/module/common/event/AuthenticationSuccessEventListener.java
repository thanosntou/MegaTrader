package com.ntouzidis.cooperative.module.common.event;

import com.ntouzidis.cooperative.module.user.entity.CustomUserDetails;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
//            AuthenticationSuccessEvent eventM = event;

            CustomUserDetails userDetails = null;

            Authentication authentication = event.getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
//                userDetails = (CustomUserDetails) authentication.getPrincipal();
                userDetails = (CustomUserDetails) authentication.getPrincipal();
            }

            System.out.println(userDetails.getUsername());

            try {
                System.out.println(userDetails.getUser().getEmail());
            } catch(Exception ex){}

//            System.out.println(userDetails.getAuthorities());

            // ....
        }
    }
}
