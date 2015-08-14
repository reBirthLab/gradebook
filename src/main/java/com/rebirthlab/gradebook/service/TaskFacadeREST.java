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

import com.rebirthlab.gradebook.entity.AcademicGroup;
import com.rebirthlab.gradebook.entity.Gradebook;
import com.rebirthlab.gradebook.entity.Student;
import com.rebirthlab.gradebook.entity.StudentGrade;
import com.rebirthlab.gradebook.entity.Task;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("tasks")
public class TaskFacadeREST extends AbstractFacade<Task> {
    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public TaskFacadeREST() {
        super(Task.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void createTask(Task task) {
        getEntityManager().persist(task);
        getEntityManager().flush();
        
        Integer gradebookId = task.getGradebookId().getGradebookId();
        Gradebook gradebook = getEntityManager().find(Gradebook.class, gradebookId);
        AcademicGroup group = gradebook.getAcademicGroupId();
        Collection<Student> students = group.getStudentCollection();
        
        for(Student student : students){
            StudentGrade grade = new StudentGrade(student.getStudentId(), task.getTaskId()); 
            short gradevalue = 0;           
            grade.setGrade(gradevalue); 
            getEntityManager().persist(grade);
        }  
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Task find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Task entity) {
        super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
