package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.gradebook.GradebookDTO;
import com.rebirthlab.gradebook.application.service.gradebook.GradebookService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.gradebook.LecturerGradebooks;
import com.rebirthlab.gradebook.domain.model.gradebook.LecturerGradebooksRepository;
import com.rebirthlab.gradebook.domain.model.gradebook.StudentGradebooks;
import com.rebirthlab.gradebook.domain.model.gradebook.StudentGradebooksRepository;
import com.rebirthlab.gradebook.domain.model.user.User;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import java.util.List;
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

@Path("gradebooks")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class GradebookController {

    private GradebookService gradebookService;
    private LecturerGradebooksRepository lecturerGradebooksRepository;
    private StudentGradebooksRepository studentGradebooksRepository;
    private UserService userService;

    @Autowired
    public GradebookController(GradebookService gradebookService,
                               LecturerGradebooksRepository lecturerGradebooksRepository,
                               StudentGradebooksRepository studentGradebooksRepository,
                               UserService userService) {
        this.gradebookService = gradebookService;
        this.lecturerGradebooksRepository = lecturerGradebooksRepository;
        this.studentGradebooksRepository = studentGradebooksRepository;
        this.userService = userService;
    }

    @POST
    public Response createGradebook(@Context SecurityContext securityContext, GradebookDTO gradebookDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER, GradebookConstants.ROLE_ADMIN)) {
            Gradebook newGradebook = gradebookService.create(gradebookDTO)
                    .orElseThrow(() -> new BadRequestException("Cannot create gradebook entity from provided data"));
            return Response.ok(newGradebook).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editGradebook(@PathParam("id") Long id, GradebookDTO gradebookDTO) {
        Gradebook updatedGradebook = gradebookService.updateById(id, gradebookDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update gradebook with provided data"));
        return Response.ok(updatedGradebook).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeGradebook(@PathParam("id") Long id) {
        gradebookService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public Response findGradebook(@PathParam("id") Long id) {
        Gradebook gradebook = gradebookService.findById(id)
                .orElseThrow(() -> new BadRequestException("No gradebook found matching id=" + id));
        return Response.ok(gradebook).build();
    }

    @GET
    public Response findAllGradebooks(@Context SecurityContext securityContext) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            User lecturer = userService.findUserByEmail(currentUserEmail).orElseThrow();
            List<LecturerGradebooks> lecturerGradebooks = lecturerGradebooksRepository.searchBy(lecturer.getId());
            if (lecturerGradebooks.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(lecturerGradebooks).build();
        }
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_STUDENT)) {
            User student = userService.findUserByEmail(currentUserEmail).orElseThrow();
            List<StudentGradebooks> studentGradebooks = studentGradebooksRepository.searchBy(student.getId());
            if (studentGradebooks.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(studentGradebooks).build();
        }
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_ADMIN)) {
            List<Gradebook> gradebooks = gradebookService.findAll();
            if (gradebooks.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(gradebooks).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

}
