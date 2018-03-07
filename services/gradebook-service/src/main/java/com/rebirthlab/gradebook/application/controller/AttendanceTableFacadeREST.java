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

import com.rebirthlab.gradebook.domain.model.AttendanceTable;
import com.rebirthlab.gradebook.application.security.AuthenticationService;
import com.rebirthlab.gradebook.application.security.CurrentUser;
import com.rebirthlab.gradebook.application.security.UserDataFinder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */

@Path("groups/{academic_group_id}/semesters/{Semesterid}/gradebooks/{gradebook_id}/attendance")
public class AttendanceTableFacadeREST extends AbstractFacade<AttendanceTable> {

    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public AttendanceTableFacadeREST() {
        super(AttendanceTable.class);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<AttendanceTable> showTable(@PathParam("academic_group_id") Integer academicGroupId,
                                           @PathParam("Semesterid") Integer semesterId,
                                           @PathParam("gradebook_id") Integer gradebookId,
                                           @HeaderParam("Authorization") String authorization) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(AttendanceTable.class);

        Root attendanceTable = cq.from(AttendanceTable.class);

        /*if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)) {
            cq.where(
                    cb.and(
                            cb.equal(attendanceTable.get(AttendanceTable_.academicGroupId), academicGroupId),
                            cb.equal(attendanceTable.get(AttendanceTable_.semesterId), semesterId),
                            cb.equal(attendanceTable.get(AttendanceTable_.gradebookId), gradebookId)
                    )
            );
            return getEntityManager().createQuery(cq).getResultList();
        }

        if (user.getRole().equals(GradebookConstants.ROLE_STUDENT)) {
            Integer studentId = user.getId();
            cq.where(
                    cb.and(
                            cb.equal(attendanceTable.get(AttendanceTable_.academicGroupId), academicGroupId),
                            cb.equal(attendanceTable.get(AttendanceTable_.semesterId), semesterId),
                            cb.equal(attendanceTable.get(AttendanceTable_.gradebookId), gradebookId),
                            cb.equal(attendanceTable.get(AttendanceTable_.studentId), studentId)
                    )
            );
            return getEntityManager().createQuery(cq).getResultList();
        }*/

        return null;

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}