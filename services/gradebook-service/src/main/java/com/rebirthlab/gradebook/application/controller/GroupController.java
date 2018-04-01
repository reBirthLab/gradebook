package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.group.GroupDTO;
import com.rebirthlab.gradebook.application.service.group.GroupService;
import com.rebirthlab.gradebook.application.service.lecturer.LecturerService;
import com.rebirthlab.gradebook.application.service.user.UserService;
import com.rebirthlab.gradebook.application.util.SecurityCheck;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.group.Group;
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

@Path("groups")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class GroupController {

    private GroupService groupService;
    private UserService userService;
    private LecturerService lecturerService;

    @Autowired
    public GroupController(GroupService groupService,
                           UserService userService,
                           LecturerService lecturerService) {
        this.groupService = groupService;
        this.userService = userService;
        this.lecturerService = lecturerService;
    }

    @POST
    @AdminRoleRequired
    public Response createGroup(GroupDTO groupDTO) {
        Optional<Group> newGroup = groupService.create(groupDTO);
        if (newGroup.isPresent()) {
            return Response.ok(newGroup.get()).build();
        }
        throw new BadRequestException("Cannot create group entity from provided data");
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editGroup(@PathParam("id") Long id, GroupDTO groupDTO) {
        Group updatedGroup = groupService.updateById(id, groupDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update group with provided data"));
        return Response.ok(updatedGroup).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeGroup(@PathParam("id") Long id) {
        groupService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @AdminRoleRequired
    public Response findGroup(@PathParam("id") Long id) {
        Group group = groupService.findById(id)
                .orElseThrow(() -> new BadRequestException("No group found matching id=" + id));
        return Response.ok(group).build();
    }

    @GET
    public Response findGroups(@Context SecurityContext securityContext) {
        if (securityContext.isUserInRole("ROLE_ADMIN")) {
            List<Group> groups = groupService.findAll();
            if (groups.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(groups).build();
        }
        String currentUserEmail = SecurityCheck.getCurrentUserEmail(securityContext);
        if (userService.isUserRole(GradebookConstants.ROLE_LECTURER, currentUserEmail)) {
            Lecturer lecturer = lecturerService.findByEmail(currentUserEmail)
                    .orElseThrow();
            Faculty lecturerFaculty = lecturer.getDepartmentId().getFacultyId();
            List<Group> lecturerFacultyGroups = groupService.findAllByFaculty(lecturerFaculty);
            if (lecturerFacultyGroups.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(lecturerFacultyGroups).build();
        }
        throw new ForbiddenException("Insufficient access rights to perform the requested operation");
    }
}
