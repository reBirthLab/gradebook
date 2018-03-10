package com.rebirthlab.gradebook.auth;

import com.rebirthlab.gradebook.auth.controller.UserController;
import com.rebirthlab.gradebook.auth.util.WebApplicationExceptionMapper;
import javax.ws.rs.ext.ExceptionMapper;
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
        register(UserController.class);
    }
}
