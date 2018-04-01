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

import com.rebirthlab.gradebook.domain.model.semester.Semester;
import java.util.List;
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
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("semesters")
public class SemesterFacadeREST {

    public SemesterFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void createSemester(@HeaderParam("Authorization") String authorization, Semester entity) {

        //String username = new AuthenticationService(.getUsername(authorization);
  /*      User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.create(entity);
        }*/
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void editSemester(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id,
                             Semester entity) {

        //String username = new AuthenticationService(.getUsername(authorization);
  /*      User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.edit(entity);
        }*/
    }

    @DELETE
    @Path("{id}")
    public void removeSemester(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id) {

        //String username = new AuthenticationService(.getUsername(authorization);
 /*       User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.remove(super.find(id));
        }*/
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Semester find(@HeaderParam("Authorization") String authorization,
                         @PathParam("id") Integer id) {

        //String username = new AuthenticationService(.getUsername(authorization);
/*        User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.find(id);
        }*/

        return null;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Semester> findSemesters(@HeaderParam("Authorization") String authorization) {

        //String username = new AuthenticationService(.getUsername(authorization);
/*        User user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.findAll();
        }

        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)) {
           return findActualSemesters();
        }*/

        return null;
    }

    private List<Semester> findActualSemesters() {
/*        Calendar today = Calendar.getInstance();
        short currentYear = (short) today.get(Calendar.YEAR);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Semester.class);
        Root semesters = cq.from(Semester.class);

        cq.where(
                cb.or(
                   *//*     cb.equal(semesters.get(Semester_.academicYear), currentYear),
                        cb.equal(semesters.get(Semester_.academicYear), currentYear + 1)*//*
                )
        );
        return getEntityManager().createQuery(cq).getResultList();*/

        return null;
    }

}
