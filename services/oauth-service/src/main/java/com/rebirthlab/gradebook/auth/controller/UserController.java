package com.rebirthlab.gradebook.auth.controller;

import com.rebirthlab.gradebook.auth.model.User;
import com.rebirthlab.gradebook.auth.service.UserDTO;
import com.rebirthlab.gradebook.auth.service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
        Set<String> userRole = new HashSet<>();
        userRole.add("ROLE_USER");
        user.setRoles(userRole);
        Optional<User> registeredUser = userService.registerUser(user);
        if (registeredUser.isPresent()) {
            return Response.ok(registeredUser.get()).build();
        }
        throw new BadRequestException("A user with email " + user.getEmail() + " already exists");
    }

    @GET
    public Response getUser(@Context SecurityContext securityContext) {
        Authentication authentication = (Authentication) securityContext.getUserPrincipal();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        return Response.ok(currentUser).build();
    }

    @PUT
    public Response updateUser(UserDTO userDTO) {
        // TODO: Implement user update logic
        throw new UnsupportedOperationException("Not implemented yet");
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
