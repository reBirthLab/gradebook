package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.attendance.AttendanceDTO;
import com.rebirthlab.gradebook.application.service.attendance.AttendanceService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendance;
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

@Path("attendances")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class AttendanceController {

    private AttendanceService attendanceService;
    private UserService userService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, UserService userService) {
        this.attendanceService = attendanceService;
        this.userService = userService;
    }

    @PUT
    @Path("{id}")
    public Response editStudentAttendance(@Context SecurityContext securityContext,
                                          @PathParam("id") Long id,
                                          AttendanceDTO attendanceDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            StudentAttendance updatedAttendance = attendanceService.updateById(id, attendanceDTO)
                    .orElseThrow(() -> new BadRequestException("Cannot update attendance with provided data"));
            return Response.ok(updatedAttendance).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

}
