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
package com.rebirthlab.gradebook.services;

import com.rebirthlab.gradebook.entities.StudentGrade;
import com.rebirthlab.gradebook.entities.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Stateless
@Path("groups/{academic_group_id}/semesters/{semester_id}/gradebooks/{gradebook_id}/tasks")
public class StudentGradeTableFacadeREST extends AbstractFacade<StudentGrade> {
    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

//    private StudentGradePK getPrimaryKey(PathSegment pathSegment) {
//        /*
//         * pathSemgent represents a URI path segment and any associated matrix parameters.
//         * URI path part is supposed to be in form of 'somePath;studentId=studentIdValue;taskId=taskIdValue'.
//         * Here 'somePath' is a result of getPath() method invocation and
//         * it is ignored in the following code.
//         * Matrix parameters are used as field names to build a primary key instance.
//         */
//        com.rebirthlab.gradebook.entities.StudentGradePK key = new com.rebirthlab.gradebook.entities.StudentGradePK();
//        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
//        java.util.List<String> studentId = map.get("studentId");
//        if (studentId != null && !studentId.isEmpty()) {
//            key.setStudentId(new java.lang.Integer(studentId.get(0)));
//        }
//        java.util.List<String> taskId = map.get("taskId");
//        if (taskId != null && !taskId.isEmpty()) {
//            key.setTaskId(new java.lang.Integer(taskId.get(0)));
//        }
//        return key;
//    }

    public StudentGradeTableFacadeREST() {
        super(StudentGrade.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(StudentGrade entity) {
        super.create(entity);
    }
//
//    @PUT
//    @Path("{id}")
//    @Consumes({"application/xml", "application/json"})
//    public void edit(@PathParam("id") PathSegment id, StudentGrade entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") PathSegment id) {
//        com.rebirthlab.gradebook.entities.StudentGradePK key = getPrimaryKey(id);
//        super.remove(super.find(key));
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public StudentGrade find(@PathParam("id") PathSegment id) {
//        com.rebirthlab.gradebook.entities.StudentGradePK key = getPrimaryKey(id);
//        return super.find(key);
//    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<StudentGrade> showTable(@PathParam("academic_group_id") Integer academic_group_id, 
            @PathParam("semester_id") Integer semester_id, 
            @PathParam("gradebook_id") Integer gradebook_id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(StudentGrade.class);
        
        Root grade = cq.from(StudentGrade.class);
        Join gradebook = grade.join(StudentGrade_.task).join(Task_.gradebookId);
        
        cq.where(
                cb.and(
                        cb.equal(gradebook.get(Gradebook_.academicGroupId), academic_group_id),
                        cb.equal(gradebook.get(Gradebook_.semesterId), semester_id),
                        cb.equal(gradebook.get(Gradebook_.gradebookId), gradebook_id)
                )
        );
        
        return getEntityManager().createQuery(cq).getResultList();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<StudentGrade> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
