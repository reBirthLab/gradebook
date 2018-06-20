package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.domain.model.task.TaskView;
import com.rebirthlab.gradebook.domain.model.task.TaskViewRepository;
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

@Path("groups/{group_id}/semesters/{semester_id}/gradebooks/{gradebook_id}/tasks")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class TasksViewController {

    private TaskViewRepository taskViewRepository;
    private UserService userService;

    @Autowired
    public TasksViewController(TaskViewRepository taskViewRepository,
                               UserService userService) {
        this.taskViewRepository = taskViewRepository;
        this.userService = userService;
    }

    @GET
    public Response getView(@Context SecurityContext securityContext,
                            @PathParam("group_id") Long groupId,
                            @PathParam("semester_id") Long semesterId,
                            @PathParam("gradebook_id") Long gradebookId) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER)) {
            List<TaskView> taskTable = taskViewRepository.searchBy(groupId, semesterId, gradebookId);
            if (taskTable.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(taskTable).build();
        }
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_STUDENT)) {
            User student = userService.findUserByEmail(currentUserEmail).orElseThrow();
            List<TaskView> taskTable = taskViewRepository.searchBy(groupId, semesterId, gradebookId,
                    student.getId());
            if (taskTable.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(taskTable).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

}
