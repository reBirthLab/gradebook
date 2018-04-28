package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.domain.model.attendance.AttendanceView;
import com.rebirthlab.gradebook.domain.model.attendance.AttendanceViewRepository;
import com.rebirthlab.gradebook.domain.model.user.User;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
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

@Path("groups/{group_id}/semesters/{semester_id}/gradebooks/{gradebook_id}/attendance")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class AttendanceViewController {

    private AttendanceViewRepository attendanceViewRepository;
    private UserService userService;

    @Autowired
    public AttendanceViewController(AttendanceViewRepository attendanceViewRepository,
                                    UserService userService) {
        this.attendanceViewRepository = attendanceViewRepository;
        this.userService = userService;
    }

    @GET
    public Response getTable(@Context SecurityContext securityContext,
                             @PathParam("group_id") Long groupId,
                             @PathParam("semester_id") Long semesterId,
                             @PathParam("gradebook_id") Long gradebookId) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            List<AttendanceView> attendanceTable = attendanceViewRepository.searchBy(groupId, semesterId, gradebookId);
            if (attendanceTable.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(attendanceTable).build();
        }
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_STUDENT)) {
            User student = userService.findUserByEmail(currentUserEmail).orElseThrow();
            List<AttendanceView> attendanceTable = attendanceViewRepository.searchBy(groupId, semesterId, gradebookId,
                    student.getId());
            if (attendanceTable.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(attendanceTable).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

}
