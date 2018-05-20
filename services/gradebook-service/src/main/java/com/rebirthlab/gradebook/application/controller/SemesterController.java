package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.semester.SemesterDTO;
import com.rebirthlab.gradebook.application.service.semester.SemesterService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.semester.Semester;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

@Path("semesters")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class SemesterController {

    private SemesterService semesterService;
    private UserService userService;

    @Autowired
    public SemesterController(SemesterService semesterService,
                              UserService userService) {
        this.semesterService = semesterService;
        this.userService = userService;
    }

    @POST
    @AdminRoleRequired
    public Response createSemester(SemesterDTO semesterDTO) {
        Optional<Semester> newSemester = semesterService.create(semesterDTO);
        if (newSemester.isPresent()) {
            return Response.ok(newSemester.get()).build();
        }
        throw new BadRequestException("Cannot create semester entity. Semester '" + semesterDTO.getName()
                + " " + semesterDTO.getAcademicYear() + "' already exists");
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editSemester(@PathParam("id") Long id, SemesterDTO semesterDTO) {
        Semester updatedSemester = semesterService.updateById(id, semesterDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update semester with provided data"));
        return Response.ok(updatedSemester).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeSemester(@PathParam("id") Long id) {
        semesterService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @AdminRoleRequired
    public Response find(@PathParam("id") Long id) {
        Semester semester = semesterService.findById(id)
                .orElseThrow(() -> new BadRequestException("No semester found matching id=" + id));
        return Response.ok(semester).build();
    }

    @GET
    public Response findSemesters(@Context SecurityContext securityContext) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_ADMIN)) {
            List<Semester> semesters = semesterService.findAll();
            if (semesters.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(semesters).build();
        }
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            List<Semester> semesters = semesterService.findActual();
            if (semesters.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(semesters).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

}
