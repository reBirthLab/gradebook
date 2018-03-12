package com.rebirthlab.gradebook.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Anastasiy
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.rebirthlab.gradebook")
@EntityScan(basePackages = "com.rebirthlab.gradebook.domain.model")
@EnableJpaRepositories(basePackages = "com.rebirthlab.gradebook.domain.model")
@EnableTransactionManagement
@EnableResourceServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
