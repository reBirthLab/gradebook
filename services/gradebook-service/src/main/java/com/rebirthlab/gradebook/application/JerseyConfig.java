package com.rebirthlab.gradebook.application;

import com.rebirthlab.gradebook.application.controller.UserController;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by Anastasiy
 */
@Component
@ApplicationPath("/api/v1")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserController.class);
    }

}
