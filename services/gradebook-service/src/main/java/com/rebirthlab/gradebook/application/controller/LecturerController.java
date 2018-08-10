package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.lecturer.LecturerDTO;
import com.rebirthlab.gradebook.application.service.lecturer.LecturerService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
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
@Path("lecturers")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class LecturerController {

    private LecturerService lecturerService;
    private UserService userService;

    @Autowired
    public LecturerController(LecturerService lecturerService, UserService userService) {
        this.lecturerService = lecturerService;
        this.userService = userService;
    }

    @POST
    @AdminRoleRequired
    public Response createLecturer(LecturerDTO lecturerDTO) {
        Optional<Lecturer> registeredLecturer = lecturerService.register(lecturerDTO);
        if (registeredLecturer.isPresent()) {
            return Response.ok(registeredLecturer.get()).build();
        }
        throw new BadRequestException("Cannot create lecturer entity from provided data");
    }

    @POST
    @Path("multiple")
    @AdminRoleRequired
    public Response createAllLecturers(List<LecturerDTO> lecturerDTO) {
        List<Lecturer> registeredLecturers = lecturerService.registerAll(lecturerDTO);
        if (registeredLecturers.isEmpty()) {
            throw new BadRequestException("Cannot create lecturer entities from provided data");
        }
        return Response.ok(registeredLecturers).build();
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editLecturer(@PathParam("id") Long id, LecturerDTO lecturerDTO) {
        Lecturer updatedLecturer = lecturerService.updateById(id, lecturerDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update lecturer account with provided data"));
        return Response.ok(updatedLecturer).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeLecturer(@PathParam("id") Long id) {
        lecturerService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public Response findLecturer(@Context SecurityContext securityContext, @PathParam("id") Long id) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_ADMIN, GradebookConstants.ROLE_LECTURER)) {
            Lecturer lecturer = lecturerService.findById(id)
                    .orElseThrow(() -> new BadRequestException("No lecturer account found matching id=" + id));
            return Response.ok(lecturer).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

    @GET
    public Response findAllLecturers(@Context SecurityContext securityContext) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_ADMIN)) {
            Optional<List<Lecturer>> lecturers = lecturerService.findAll();
            if (lecturers.isPresent()) {
                return Response.ok(lecturers.get()).build();
            }
            return Response.noContent().build();
        }
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            Lecturer lecturer = lecturerService.findByEmail(currentUserEmail)
                    .orElseThrow();
            List<Lecturer> lecturers = lecturerService.findAllByDepartmentId(lecturer.getDepartmentId().getId())
                    .orElseThrow();
            return Response.ok(lecturers).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

    @GET
    @Path("/me")
    public Response findMe(@Context SecurityContext securityContext) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Lecturer lecturer = lecturerService.findByEmail(currentUserEmail)
                .orElseThrow(() -> new BadRequestException("No lecturer account found for current user" +
                        " [ " + currentUserEmail + " ]"));
        return Response.ok(lecturer).build();
    }

    @PUT
    @Path("/me")
    public Response updateMe(@Context SecurityContext securityContext, LecturerDTO lecturerDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Lecturer updatedLecturer = lecturerService.updateByEmail(currentUserEmail, lecturerDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update lecturer account of current user" +
                        " [ " + currentUserEmail + " ]"));
        return Response.ok(updatedLecturer).build();
    }

}
