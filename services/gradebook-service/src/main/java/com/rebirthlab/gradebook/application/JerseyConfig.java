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
        register(UserController.class);
        register(AdminController.class);
        register(LecturerController.class);
        register(StudentController.class);
        register(DepartmentController.class);
        register(FacultyController.class);
        register(GroupController.class);
        register(GradebookController.class);
        register(SemesterController.class);
        register(AttendanceViewController.class);
        register(AttendanceController.class);
        register(GradeController.class);
    }

}
