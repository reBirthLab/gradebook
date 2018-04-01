package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.department.DepartmentDTO;
import com.rebirthlab.gradebook.application.service.department.DepartmentService;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.department.Department;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Path("departments")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @POST
    @AdminRoleRequired
    public Response createDepartment(DepartmentDTO departmentDTO) {
        Optional<Department> newDepartment = departmentService.create(departmentDTO);
        if (newDepartment.isPresent()) {
            return Response.ok(newDepartment.get()).build();
        }
        throw new BadRequestException("Cannot create department entity from provided data");
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editDepartment(@PathParam("id") Long id, DepartmentDTO departmentDTO) {
        Department updatedDepartment = departmentService.updateById(id, departmentDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update department with provided data"));
        return Response.ok(updatedDepartment).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeDepartment(@PathParam("id") Long id) {
        departmentService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @AdminRoleRequired
    public Response findDepartment(@PathParam("id") Long id) {
        Department department = departmentService.findById(id)
                .orElseThrow(() -> new BadRequestException("No department found matching id=" + id));
        return Response.ok(department).build();
    }

    @GET
    @AdminRoleRequired
    public Response findAllDepartments() {
        List<Department> departments = departmentService.findAll();
        if (!departments.isEmpty()) {
            return Response.ok(departments).build();
        }
        return Response.noContent().build();
    }

}
