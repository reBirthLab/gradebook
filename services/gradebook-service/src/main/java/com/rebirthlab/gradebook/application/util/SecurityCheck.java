package com.rebirthlab.gradebook.application.util;

import com.rebirthlab.gradebook.application.service.user.UserDetails;
import javax.ws.rs.core.SecurityContext;
import org.springframework.security.core.Authentication;

/**
 * Created by Anastasiy
 */
public class SecurityCheck {

    public static String getCurrentUserEmail(SecurityContext securityContext) {
        Authentication authentication = (Authentication) securityContext.getUserPrincipal();
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        return authenticatedUser.getUsername();
    }

    public static boolean isCurrentUserEmail(String email, SecurityContext securityContext) {
        Authentication authentication = (Authentication) securityContext.getUserPrincipal();
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        return email.equals(authenticatedUser.getUsername());
    }

}
