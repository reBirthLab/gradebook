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
package com.rebirthlab.gradebook.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@javax.ws.rs.ApplicationPath("api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.rebirthlab.gradebook.config.CrossOriginResourceSharingFilter.class);
        resources.add(com.rebirthlab.gradebook.security.JaxRsFilterAuthentication.class);
        resources.add(com.rebirthlab.gradebook.service.AcademicGroupFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.AdminFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.AttendanceTableFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.DepartmentFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.FacultyFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.GradebookFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.LecturerFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.SemesterFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.StudentAttendanceFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.StudentFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.StudentGradeFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.TaskFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.TasksTableFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.UserGradebooksFacadeREST.class);
        resources.add(com.rebirthlab.gradebook.service.UsersFacadeREST.class);
    }
    
}
