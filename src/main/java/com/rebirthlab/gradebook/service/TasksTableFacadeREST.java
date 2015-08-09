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

import com.rebirthlab.gradebook.entity.Gradebook;
import com.rebirthlab.gradebook.entity.Task;
import com.rebirthlab.gradebook.entity.TasksTable;
import com.rebirthlab.gradebook.entity.TasksTable_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
@Path("groups/{academic_group_id}/semesters/{semester_id}/gradebooks/{gradebook_id}/tasks")
public class TasksTableFacadeREST extends AbstractFacade<TasksTable> {
    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public TasksTableFacadeREST() {
        super(TasksTable.class);
    }

    /**
     * Creates a new task
     * @param gradebookId Gradebook ID from URI
     * @param entity Task from JSON
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public void createTask(@PathParam("gradebook_id") Integer gradebookId, Task entity) {
        entity.setGradebookId(getEntityManager().find(Gradebook.class, gradebookId));
        getEntityManager().persist(entity);
    }
    
    // TODO: Update task
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, TasksTable entity) {
        super.edit(entity);
    }
    
    /**
     * Returns list of student grades based on provided via URI parameters
     * 
     * @param academicGroupId Academic group ID
     * @param semesterId Semester ID
     * @param gradebookId Gradebook ID
     * @return List of student grades
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<TasksTable> showTable(@PathParam("academic_group_id") Integer academicGroupId,
            @PathParam("semester_id") Integer semesterId,
            @PathParam("gradebook_id") Integer gradebookId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(TasksTable.class);
        
        Root tasksTable = cq.from(TasksTable.class);
        cq.where(
                cb.and(
                        cb.equal(tasksTable.get(TasksTable_.academicGroupId), academicGroupId),
                        cb.equal(tasksTable.get(TasksTable_.semesterId), semesterId),
                        cb.equal(tasksTable.get(TasksTable_.gradebookId), gradebookId)
                )
        );
        
        return getEntityManager().createQuery(cq).getResultList();
    }

    // TODO: Get the row of grades for specific student
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TasksTable find(@PathParam("id") String id) {
        return super.find(id);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
