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
package com.rebirthlab.gradebook.application.controller;

import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.application.security.AuthenticationService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("gradebooks")
public class GradebookFacadeREST extends AbstractFacade<Gradebook> {

    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public GradebookFacadeREST() {
        super(Gradebook.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void createGradebook(@HeaderParam("Authorization") String authorization, Gradebook entity) {

        String username = new AuthenticationService().getUsername(authorization);
     /*   User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)
                || user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.create(entity);
        }*/
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void editGradebook(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id,
                              Gradebook entity) {

        String username = new AuthenticationService().getUsername(authorization);
  /*      User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.edit(entity);
        }*/
    }

    @DELETE
    @Path("{id}")
    public void removeGradebook(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id) {

        String username = new AuthenticationService().getUsername(authorization);
      /*  User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.remove(super.find(id));
        }*/
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Gradebook findGradebook(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List findAllGradebooks(@HeaderParam("Authorization") String authorization) {

        String username = new AuthenticationService().getUsername(authorization);

       //User user = UserDataFinder.findDataBy(username);

      /*  if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)) {
            Integer lecturerId = user.getId();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(LecturerGradebooks.class);
            Root lecturerGradebooks = cq.from(LecturerGradebooks.class);
            cq.where(cb.equal(lecturerGradebooks.get(LecturerGradebooks_.lecturerId), lecturerId));

            return getEntityManager().createQuery(cq).getResultList();
        }

        if (user.getRole().equals(GradebookConstants.ROLE_STUDENT)) {
            Integer studentId = user.getId();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(StudentGradebooks.class);
            Root studentGradebooks = cq.from(StudentGradebooks.class);
            cq.where(cb.equal(studentGradebooks.get(StudentGradebooks_.studentId), studentId));

            return getEntityManager().createQuery(cq).getResultList();
        }*/

        /*if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gradebook.class));

            return getEntityManager().createQuery(cq).getResultList();
        }*/

        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
