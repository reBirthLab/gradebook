/*
 * Copyright (C) 2015 Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rebirthlab.gradebook.service;

import com.rebirthlab.gradebook.common.GradebookConstants;
import com.rebirthlab.gradebook.entity.AcademicGroup;
import com.rebirthlab.gradebook.entity.AcademicGroup_;
import com.rebirthlab.gradebook.entity.Lecturer;
import com.rebirthlab.gradebook.security.AuthenticationService;
import com.rebirthlab.gradebook.security.CurrentUser;
import com.rebirthlab.gradebook.security.UserDataFinder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Stateless
@Path("groups")
public class AcademicGroupFacadeREST extends AbstractFacade<AcademicGroup> {
    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public AcademicGroupFacadeREST() {
        super(AcademicGroup.class);
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public void createGroup(@HeaderParam("Authorization") String authorization, AcademicGroup entity) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.create(entity);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void editGroup(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id, AcademicGroup entity) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.edit(entity);
        }
    }

    @DELETE
    @Path("{id}")
    public void removeGroup(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.remove(super.find(id));
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public AcademicGroup findGroup(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id) {
        
        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.find(id);
        }
        
        return null;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<AcademicGroup> findGroups(@HeaderParam("Authorization") String authorization){

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.findAll();
        }
        
        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)) {
            Integer lecturerId = user.getId();
            Lecturer lecturer = getEntityManager().find(Lecturer.class, lecturerId);          
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(AcademicGroup.class);
            Root groups = cq.from(AcademicGroup.class);
            
            cq.where(cb.equal(groups.get(AcademicGroup_.facultyId), lecturer.getDepartmentId().getFacultyId()));
            return getEntityManager().createQuery(cq).getResultList();
        }
        
        return null;     
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
