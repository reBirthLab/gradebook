package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.admin.AdminDTO;
import com.rebirthlab.gradebook.application.service.admin.AdminService;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.user.Admin;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("administrators")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @POST
    @AdminRoleRequired
    public Response createAdmin(AdminDTO adminDTO) {
        Optional<Admin> registeredAdmin = adminService.register(adminDTO);
        if (registeredAdmin.isPresent()) {
            return Response.ok(registeredAdmin.get()).build();
        }
        throw new BadRequestException("Cannot create admin entity from provided data");
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editAdmin(@PathParam("id") Long id, AdminDTO adminDTO) {
        Admin updatedAdmin = adminService.updateById(id, adminDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update admin account with provided data"));
        return Response.ok(updatedAdmin).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeAdmin(@PathParam("id") Long id) {
        adminService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @AdminRoleRequired
    public Response findAdmin(@PathParam("id") Long id) {
        Admin admin = adminService.findById(id)
                .orElseThrow(() -> new BadRequestException("No admin account found matching id=" + id));
        return Response.ok(admin).build();
    }

    @GET
    @AdminRoleRequired
    public Response findAllAdmins() {
        Optional<List<Admin>> admins = adminService.findAll();
        if (admins.isPresent()) {
            return Response.ok(admins.get()).build();
        }
        return Response.noContent().build();

    }

}
