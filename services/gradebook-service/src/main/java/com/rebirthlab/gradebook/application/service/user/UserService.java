package com.rebirthlab.gradebook.application.service.user;

import com.rebirthlab.gradebook.domain.model.user.User;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface UserService {

    Optional<User> getUserByEmail(String email);

    List<User> findAllUsers();
}
