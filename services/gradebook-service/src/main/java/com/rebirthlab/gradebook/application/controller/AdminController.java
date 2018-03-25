package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.admin.AdminDTO;
import com.rebirthlab.gradebook.application.service.admin.AdminService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.user.Admin;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("administrators")
@Scope("request")
@AdminRoleRequired
@Produces(MediaType.APPLICATION_JSON)
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @POST
    public Response createAdmin(AdminDTO adminDTO) {
        Optional<Admin> registeredAdmin = adminService.register(adminDTO);
        if (registeredAdmin.isPresent()) {
            return Response.ok(registeredAdmin.get()).build();
        }
        throw new BadRequestException("Cannot create admin entity from provided data");
    }

    @PUT
    @Path("{id}")
    public Response editAdmin(@PathParam("id") Long id, AdminDTO adminDTO) {
        Admin updatedAdmin = adminService.updateById(id, adminDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update admin account with provided data"));
        return Response.ok(updatedAdmin).build();
    }

    @DELETE
    @Path("{id}")
    public Response removeAdmin(@PathParam("id") Long id) {
        adminService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public Response findAdmin(@PathParam("id") Long id) {
        Admin admin = adminService.findById(id)
                .orElseThrow(() -> new BadRequestException("No admin account found matching id=" + id));
        return Response.ok(admin).build();
    }

    @GET
    public Response findAllAdmins() {
        Optional<List<Admin>> admins = adminService.findAll();
        if (admins.isPresent()) {
            return Response.ok(admins.get()).build();
        }
        return Response.noContent().build();

    }

    @GET
    @Path("/me")
    public Response findMe(@Context SecurityContext securityContext) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Admin admin = adminService.findByEmail(currentUserEmail)
                .orElseThrow(() -> new BadRequestException("No admin account found for current user" +
                        " [ " + currentUserEmail + " ]"));
        return Response.ok(admin).build();
    }

    @PUT
    @Path("/me")
    public Response updateMe(@Context SecurityContext securityContext, AdminDTO adminDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Admin updatedLecturer = adminService.updateByEmail(currentUserEmail, adminDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update admin account of current user" +
                        " [ " + currentUserEmail + " ]"));
        return Response.ok(updatedLecturer).build();
    }

}
