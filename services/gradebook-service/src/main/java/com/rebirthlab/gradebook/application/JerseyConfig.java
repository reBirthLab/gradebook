package com.rebirthlab.gradebook.application;

import com.rebirthlab.gradebook.application.controller.StudentController;
import com.rebirthlab.gradebook.application.controller.UserController;
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
        register(StudentController.class);
    }

}
