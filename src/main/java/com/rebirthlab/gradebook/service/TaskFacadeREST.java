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
import com.rebirthlab.gradebook.entity.Gradebook;
import com.rebirthlab.gradebook.entity.Student;
import com.rebirthlab.gradebook.entity.StudentAttendance;
import com.rebirthlab.gradebook.entity.StudentGrade;
import com.rebirthlab.gradebook.entity.Task;
import com.rebirthlab.gradebook.security.AuthenticationService;
import com.rebirthlab.gradebook.security.CurrentUser;
import com.rebirthlab.gradebook.security.UserDataFinder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("tasks")
public class TaskFacadeREST extends AbstractFacade<Task> {

    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public TaskFacadeREST() {
        super(Task.class);
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void createTask(@HeaderParam("Authorization") String authorization, Task task) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)
                || user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {

            em.persist(task);
            em.flush();

            Integer gradebookId = task.getGradebookId().getGradebookId();
            Gradebook gradebook = em.find(Gradebook.class, gradebookId);
            AcademicGroup group = gradebook.getAcademicGroupId();
            Collection<Student> students = group.getStudentCollection();

            initializeStudentGrades(task, students);

            initializeStudentAttendance(task, students);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void editTask(@PathParam("id") Integer id,
            @HeaderParam("Authorization") String authorization,
            Task task) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)
                || user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {

            Task oldTask = em.find(Task.class, id);
            if (task.getStartDate().getTime() != oldTask.getStartDate().getTime()
                    || task.getTaskLength() != oldTask.getTaskLength()
                    || task.getOnCourseMon() != oldTask.getOnCourseMon()
                    || task.getOnCourseTue() != oldTask.getOnCourseTue()
                    || task.getOnCourseWed() != oldTask.getOnCourseWed()
                    || task.getOnCourseThu() != oldTask.getOnCourseThu()
                    || task.getOnCourseFri() != oldTask.getOnCourseFri()
                    || task.getMaxGrade() != oldTask.getMaxGrade()) {
                em.remove(em.merge(oldTask));
                task.setTaskId(null);
                createTask(authorization, task);
            } else {
                em.merge(task);
            }
        }
    }

    @DELETE
    @Path("{id}")
    public void removeTask(@HeaderParam("Authorization") String authorization, @PathParam("id") Integer id) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            super.remove(super.find(id));
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Task findTask(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Task> findAllTasks(@HeaderParam("Authorization") String authorization) {

        String username = new AuthenticationService().getUsername(authorization);
        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_ADMIN)) {
            return super.findAll();
        }
        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private void initializeStudentGrades(Task task, Collection<Student> students) {
        for (Student student : students) {
            StudentGrade grade = new StudentGrade(student.getStudentId(), task.getTaskId());
            short gradevalue = 0;
            grade.setGrade(gradevalue);
            em.persist(grade);
        }
    }

    private List<Date> calculateClassDates(Task task) {
        Calendar calendar = Calendar.getInstance();
        int firstDayOfWeek = 2;
        calendar.setFirstDayOfWeek(firstDayOfWeek);

        Date startDate = task.getStartDate();
        calendar.setTime(startDate);

        int startDateDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, -startDateDayOfWeek + firstDayOfWeek);

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

        return classDates;
    }

    private void initializeStudentAttendance(Task task, Collection<Student> students) {

        List<Date> classDates = calculateClassDates(task);

        for (Student student : students) {
            for (Date date : classDates) {
                StudentAttendance attendance = new StudentAttendance();
                attendance.setStudentId(student);
                attendance.setTaskId(task);
                attendance.setClassDate(date);
                em.persist(attendance);
            }
        }
    }
}
