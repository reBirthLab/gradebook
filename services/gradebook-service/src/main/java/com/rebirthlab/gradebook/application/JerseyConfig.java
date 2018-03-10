package com.rebirthlab.gradebook.application;

import com.rebirthlab.gradebook.application.controller.UserController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by Anastasiy
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserController.class);
    }

}
