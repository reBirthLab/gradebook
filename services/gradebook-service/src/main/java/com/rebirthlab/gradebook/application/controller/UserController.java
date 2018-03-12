package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.user.AbstractUserDTO;
import com.rebirthlab.gradebook.application.service.user.UserDetails;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.domain.model.user.User;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Path("users")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService;
    private TokenStore tokenStore;

    @Autowired
    public UserController(UserService userService, TokenStore tokenStore) {
        this.userService = userService;
        this.tokenStore = tokenStore;
    }

    @POST
    @Transactional
    public Response createUser(AbstractUserDTO user) {
        Object createdUser = null;
        return Response.ok(createdUser).build();
    }

    @GET
    @Path("check")
    public Response check(@Context SecurityContext securityContext) {
        Authentication authentication = (Authentication) securityContext.getUserPrincipal();
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();

        Optional<User> currentUser = userService.getUserByEmail(authenticatedUser.getUsername());
        if (currentUser.isPresent()) {
            return Response.ok(currentUser).build();
        }
        throw new BadRequestException("No account was found associated with user email "
                + authenticatedUser.getUsername());
    }

    @GET
    public List<User> findAllUsers(@Context HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Insufficient access rights to perform the requested operation");
        }
        return userService.findAllUsers();
    }
}
