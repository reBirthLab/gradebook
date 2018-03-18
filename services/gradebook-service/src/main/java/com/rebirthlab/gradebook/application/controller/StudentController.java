package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.student.StudentDTO;
import com.rebirthlab.gradebook.application.service.student.StudentService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.user.Student;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("students")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @POST
    @Transactional
    @AdminRoleRequired
    public Response createStudent(StudentDTO studentDTO) {
        return null;
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editStudent(@PathParam("id") Long id, StudentDTO studentDTO) {
        return null;
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeStudent(@PathParam("id") Long id) {
        studentService.delete(id);
        return Response.noContent().build();

    }

    @GET
    @AdminRoleRequired
    public Response findAllStudents() {
        Optional<List<Student>> students = studentService.findAll();
        if (students.isPresent()) {
            return Response.ok(students.get()).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public Response findStudent(@Context SecurityContext securityContext,
                                @PathParam("id") Long id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new BadRequestException("No student user found matching id=" + id));
        if (SecurityCheck.isCurrentUserEmail(student.getEmail(), securityContext)) {
            return Response.ok(student).build();
        }
        throw new NotAuthorizedException("Access Denied. You are not authorized to access this resource", "");
    }

}
