package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.task.TaskDTO;
import com.rebirthlab.gradebook.application.service.task.TaskService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.task.Task;
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

@Path("tasks")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class TaskController {

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @POST
    public Response createTask(@Context SecurityContext securityContext, TaskDTO taskDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER, GradebookConstants.ROLE_ADMIN)) {
            Task newTask = taskService.create(taskDTO)
                    .orElseThrow(() -> new BadRequestException("Cannot create task entity from provided data"));
            return Response.ok(newTask).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

    @PUT
    @Path("{id}")
    public Response editTask(@Context SecurityContext securityContext, @PathParam("id") Long id, TaskDTO taskDTO) {
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(currentUserEmail, GradebookConstants.ROLE_LECTURER, GradebookConstants.ROLE_ADMIN)) {
            Task updatedTask = taskService.updateById(id, taskDTO)
                    .orElseThrow(() -> new BadRequestException("Cannot update task with provided data"));
            return Response.ok(updatedTask).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeTask(@PathParam("id") Long id) {
        taskService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public Response findTask(@PathParam("id") Long id) {
        Task task = taskService.findById(id)
                .orElseThrow(() -> new BadRequestException("No task found matching id=" + id));
        return Response.ok(task).build();
    }

    @GET
    @AdminRoleRequired
    public Response findAllTasks() {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(tasks).build();
    }

}
