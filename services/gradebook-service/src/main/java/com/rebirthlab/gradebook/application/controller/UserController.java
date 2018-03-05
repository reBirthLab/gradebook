package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.user.AbstractUserDTO;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.domain.model.user.User;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import com.rebirthlab.gradebook.application.security.AuthenticationService;
import com.rebirthlab.gradebook.application.security.CurrentUser;
import com.rebirthlab.gradebook.application.security.UserDataFinder;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

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

    @POST
    @Transactional
    public Response createUser(AbstractUserDTO user) {
        Object createdUser = userService.registerUser(user);
        return Response.ok(createdUser).build();
    }

    @GET
    @Path("check")
    public CurrentUser check(@HeaderParam("Authorization") String authorization) {
        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);
        user.setPassword(null);
        return user;
    }

    @GET
    public List<User> findAllUsers(@HeaderParam("Authorization") String authorization) {
        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);
        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            //return super.findAll();
        }
        return null;
    }

}
