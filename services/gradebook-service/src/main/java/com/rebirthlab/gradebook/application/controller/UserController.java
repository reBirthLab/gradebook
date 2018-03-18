package com.rebirthlab.gradebook.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.user.User;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Path("users")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("check")
    public Response check(@Context SecurityContext securityContext) throws JsonProcessingException {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Optional<User> currentUserAccount = userService.findUserByEmail(currentUserEmail);
        if (currentUserAccount.isPresent()) {
            return Response.ok(currentUserAccount.get()).build();
        }
        throw new BadRequestException("No account was found associated with user email "
                + currentUserEmail);
    }

    @GET
    @AdminRoleRequired
    public Response findAllUsers() {
        Optional<List<User>> users = userService.findAllUsers();
        if (users.isPresent()) {
            return Response.ok(users.get()).build();
        }
        return Response.noContent().build();
    }
}
