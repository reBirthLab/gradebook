package com.rebirthlab.gradebook.auth.model;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);

}
