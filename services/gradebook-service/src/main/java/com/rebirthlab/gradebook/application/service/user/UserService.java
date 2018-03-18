package com.rebirthlab.gradebook.application.service.user;

import com.rebirthlab.gradebook.domain.model.user.User;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface UserService {

    Optional<User> findUserByEmail(String email);

    Optional<List<User>> findAllUsers();
}
