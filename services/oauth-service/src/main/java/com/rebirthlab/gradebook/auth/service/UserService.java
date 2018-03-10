package com.rebirthlab.gradebook.auth.service;

import com.rebirthlab.gradebook.auth.model.User;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Anastasiy
 */
public interface UserService {

    Optional<User> registerUser(UserDTO user);

    Optional<User> updateUser(User user);

    Optional<User> deleteUserByEmail(String email);
}
