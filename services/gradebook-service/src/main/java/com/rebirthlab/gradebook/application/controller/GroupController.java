package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.domain.model.group.Group;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("groups")
public class GroupController {

    public GroupController() {
    }

    @POST

    public void createGroup(Group entity) {

        //String username = new AuthenticationService(.getUsername(authorization);
        /*User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.create(entity);
        }*/
    }

    @PUT
    @Path("{id}")

    public void editGroup(@PathParam("id") Integer id,
                          Group entity) {

        //String username = new AuthenticationService(.getUsername(authorization);
        /*User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.edit(entity);
        }*/
    }

    @DELETE
    @Path("{id}")
    public void removeGroup(@PathParam("id") Integer id) {

        //String username = new AuthenticationService(.getUsername(authorization);
        /*User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.remove(super.find(id));
        }*/
    }

    @GET
    @Path("{id}")

    public Group findGroup(@PathParam("id") Integer id) {

        //String username = new AuthenticationService(.getUsername(authorization);
       /* User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.find(id);
        }*/

        return null;
    }

    @GET

    public List<Group> findGroups() {

        //String username = new AuthenticationService(.getUsername(authorization);
        /*User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.findAll();
        }

        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)) {
            Integer lecturerId = user.getId();
            Lecturer lecturer = getEntityManager().find(Lecturer.class, lecturerId);

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(Group.class);
            Root groups = cq.from(Group.class);

            //cq.where(cb.equal(groups.get(AcademicGroup_.facultyId), lecturer.getId().getFacultyId()));
            return getEntityManager().createQuery(cq).getResultList();
        }*/

        return null;
    }
}
