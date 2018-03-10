package com.rebirthlab.gradebook.auth.controller;

import com.rebirthlab.gradebook.auth.model.User;
import com.rebirthlab.gradebook.auth.service.UserDTO;
import com.rebirthlab.gradebook.auth.service.UserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Path("/user")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/register")
    @Transactional
    public Response createUser(UserDTO user) {
        Optional<User> registeredUser = userService.registerUser(user);
        if (registeredUser.isPresent()) {
            return Response.ok(registeredUser.get()).build();
        }
        throw new BadRequestException("A user with email " + user.getEmail() + " already exists");
    }

    @GET
    public Response getUser(@Context SecurityContext securityContext) {
        Authentication authentication = (Authentication) securityContext.getUserPrincipal();
        Optional<UserDetails> currentUser = Optional.ofNullable((UserDetails) authentication.getPrincipal());
        if (currentUser.isPresent()) {
            return Response.ok(currentUser).build();
        }
        throw new BadRequestException("The user has not been authenticated");
    }

    @DELETE
    public Response deleteUser(@Context HttpServletRequest request,
                               @QueryParam("email") String email) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            throw new ForbiddenException("Insufficient access rights to perform the requested operation");
        }
        Optional<User> deletedUser = userService.deleteUserByEmail(email);
        if (deletedUser.isPresent()) {
            return Response.noContent().build();
        }
        throw new BadRequestException("The user with email " + email + " doesn't exist");
    }

}
