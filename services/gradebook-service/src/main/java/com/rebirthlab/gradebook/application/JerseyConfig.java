package com.rebirthlab.gradebook.application;

import com.rebirthlab.gradebook.application.controller.*;
import com.rebirthlab.gradebook.application.util.WebApplicationExceptionMapper;
import com.rebirthlab.gradebook.application.validation.AdminRoleRequiredRequestFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Anastasiy
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(WebApplicationExceptionMapper.class);
        register(AdminRoleRequiredRequestFilter.class);
        register(AdminController.class);
        register(AttendanceController.class);
        register(AttendanceViewController.class);
        register(DepartmentController.class);
        register(FacultyController.class);
        register(GradebookController.class);
        register(GradeController.class);
        register(GroupController.class);
        register(LecturerController.class);
        register(SemesterController.class);
        register(StudentController.class);
        register(TaskController.class);
        register(TasksViewController.class);
        register(UserController.class);
    }

}
