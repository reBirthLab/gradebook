package com.rebirthlab.gradebook.application.validation;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Anastasiy
 */
@AdminRoleRequired
public class AdminRoleRequiredRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        SecurityContext securityContext = requestContext.getSecurityContext();
        if (!securityContext.isUserInRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Insufficient access rights to perform the requested operation");
        }
    }
}
