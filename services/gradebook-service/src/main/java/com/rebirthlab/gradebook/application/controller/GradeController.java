package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.grade.GradeDTO;
import com.rebirthlab.gradebook.application.service.grade.GradeService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.domain.model.grade.StudentGrade;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
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

@Path("students/{student_id}/tasks/{task_id}/grade")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class GradeController {

    private GradeService gradeService;
    private UserService userService;

    @Autowired
    public GradeController(GradeService gradeService, UserService userService) {
        this.gradeService = gradeService;
        this.userService = userService;
    }

    @PUT
    public Response editGrade(@Context SecurityContext securityContext,
                              @PathParam("student_id") Long studentId,
                              @PathParam("task_id") Long taskId,
                              GradeDTO gradeDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            StudentGrade updatedGrade = gradeService.updateByStudentIdAndTaskId(studentId, taskId, gradeDTO)
                    .orElseThrow(() -> new BadRequestException("Cannot update grade with provided data"));
            return Response.ok(updatedGrade).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

}
