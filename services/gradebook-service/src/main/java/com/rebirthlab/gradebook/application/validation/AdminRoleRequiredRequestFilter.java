package com.rebirthlab.gradebook.application.validation;

import com.rebirthlab.gradebook.application.service.user.UserDetails;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by Anastasiy
 */
@Component
@AdminRoleRequired
public class AdminRoleRequiredRequestFilter implements ContainerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        SecurityContext securityContext = requestContext.getSecurityContext();
        OAuth2Authentication currentUserAuth = (OAuth2Authentication) securityContext.getUserPrincipal();
        UserDetails currentUserDetails = (UserDetails) currentUserAuth.getPrincipal();
        if (!userService.isUserRole(currentUserDetails.getUsername(), GradebookConstants.ROLE_ADMIN)) {
            throw new ForbiddenException("Insufficient access rights to perform the requested operation");
        }
    }
}
