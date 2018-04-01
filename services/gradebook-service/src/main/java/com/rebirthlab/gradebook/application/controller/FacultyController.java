package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.application.service.faculty.FacultyDTO;
import com.rebirthlab.gradebook.application.service.faculty.FacultyService;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequired;
import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("faculties")
@Scope("request")
@Produces(MediaType.APPLICATION_JSON)
public class FacultyController {

    private FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @POST
    @AdminRoleRequired
    public Response createFaculty(FacultyDTO facultyDTO) {
        Optional<Faculty> newFaculty = facultyService.create(facultyDTO);
        if (newFaculty.isPresent()) {
            return Response.ok(newFaculty.get()).build();
        }
        throw new BadRequestException("Cannot create faculty entity. Faculty '" + facultyDTO.getName()
                + "' already exists");
    }

    @PUT
    @Path("{id}")
    @AdminRoleRequired
    public Response editFaculty(@PathParam("id") Long id, FacultyDTO facultyDTO) {
        Faculty updatedFaculty = facultyService.updateById(id, facultyDTO)
                .orElseThrow(() -> new BadRequestException("Cannot update faculty with provided data"));
        return Response.ok(updatedFaculty).build();
    }

    @DELETE
    @Path("{id}")
    @AdminRoleRequired
    public Response removeFaculty(@PathParam("id") Long id) {
        facultyService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    @AdminRoleRequired
    public Response findFaculty(@PathParam("id") Long id) {
        Faculty faculty = facultyService.findById(id)
                .orElseThrow(() -> new BadRequestException("No faculty found matching id=" + id));
        return Response.ok(faculty).build();
    }

    @GET
    @AdminRoleRequired
    public Response findAllFaculties() {
        List<Faculty> faculties = facultyService.findAll();
        if (!faculties.isEmpty()) {
            return Response.ok(faculties).build();
        }
        return Response.noContent().build();
    }

}
