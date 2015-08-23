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
import com.rebirthlab.gradebook.entity.StudentAttendance;
import com.rebirthlab.gradebook.entity.StudentGrade;
import com.rebirthlab.gradebook.entity.Task;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
        
        //Initialises student grades
        for(Student student : students){
            StudentGrade grade = new StudentGrade(student.getStudentId(), task.getTaskId()); 
            short gradevalue = 0;           
            grade.setGrade(gradevalue); 
            getEntityManager().persist(grade);
        } 
        
        //Initialises student attendance      
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date startDate = task.getStartDate();
        calendar.setTime(startDate);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        
        int startDateDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, - startDateDayOfWeek + firstDayOfWeek);
        
        List<Integer> courseDays = new ArrayList<>();
        if (task.getOnCourseMon()) {
            courseDays.add(firstDayOfWeek);
        }
        if (task.getOnCourseTue()) {
            courseDays.add(firstDayOfWeek + 1);
        }
        if (task.getOnCourseWed()) {
            courseDays.add(firstDayOfWeek + 2);
        }
        if (task.getOnCourseThu()) {
            courseDays.add(firstDayOfWeek + 3);
        }
        if (task.getOnCourseFri()) {
            courseDays.add(firstDayOfWeek + 4);
        }
        
        int taskLengthInDays = task.getTaskLength() * 7;
        List<Date> classDates = new ArrayList<>();
        
        for (int i = 0; i < taskLengthInDays; i++) {
            int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            for (int courseDay : courseDays) {
                if (currentDayOfWeek == courseDay) {
                    classDates.add(calendar.getTime());
                }
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        for (Student student : students) {
            for (Date date : classDates) {
                StudentAttendance attendance = new StudentAttendance();
                attendance.setStudentId(student);
                attendance.setTaskId(task);
                attendance.setClassDate(date);
                getEntityManager().persist(attendance);
            }      
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
