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
    @AdminRoleRequired
    public Response createStudent(StudentDTO studentDTO) {
        Optional<Student> registeredStudent = studentService.register(studentDTO);
        if (registeredStudent.isPresent()) {
            return Response.ok(registeredStudent.get()).build();
        }
        throw new BadRequestException("Cannot create student entity from provided data");
    }

    @POST
    @Path("multiple")
    @AdminRoleRequired
    public Response createAllStudents(List<StudentDTO> studentDTO) {
        List<Student> registeredStudents = studentService.registerAll(studentDTO);
        if (registeredStudents.isEmpty()) {
            throw new BadRequestException("Cannot create student entities from provided data");
        }
        return Response.ok(registeredStudents).build();
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editStudent(@PathParam("id") Long id, StudentDTO studentDTO) {
        Student updatedStudent = studentService.updateById(id, studentDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update student account with provided data"));
        return Response.ok(updatedStudent).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeStudent(@PathParam("id") Long id) {
        studentService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @AdminRoleRequired
    public Response findStudent(@PathParam("id") Long id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new BadRequestException("No student account found matching id=" + id));
        return Response.ok(student).build();
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
    @Path("/me")
    public Response findMe(@Context SecurityContext securityContext) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Student student = studentService.findByEmail(currentUserEmail)
                .orElseThrow(() -> new BadRequestException("No student account found for current user" +
                        " [ " + currentUserEmail + " ]"));
        return Response.ok(student).build();
    }

    @PUT
    @Path("/me")
    public Response updateMe(@Context SecurityContext securityContext, StudentDTO studentDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        Student updatedStudent = studentService.updateByEmail(currentUserEmail, studentDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update student account of current user" +
                        " [ " + currentUserEmail + " ]"));
        return Response.ok(updatedStudent).build();
    }

}
