package com.rebirthlab.gradebook.auth.service;

import com.rebirthlab.gradebook.auth.model.User;
import com.rebirthlab.gradebook.auth.model.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Anastasiy
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> registerUser(UserDTO userDTO) {
        boolean userExists = userRepository.existsById(userDTO.getEmail());
        if (userExists) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("User with email {} already exists", userDTO.getEmail());
            }
            return Optional.empty();
        }
        String password = userDTO.getPassword();
        userDTO.setPassword(passwordEncoder.encode(password));

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        userDTO.setRoles(roles);

        userDTO.setAccountNonExpired(true);
        userDTO.setAccountNonLocked(true);
        userDTO.setCredentialsNonExpired(true);
        userDTO.setEnabled(true);

        User user = new User(userDTO);
        User registeredUser = userRepository.save(user);
        return Optional.of(registeredUser);
    }

    @Override
    public Optional<User> updateUser(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return Optional.empty();
        }
        if (userRepository.existsById(user.getUsername())) {
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findById(email);
        if (user.isPresent()) {
            userRepository.deleteById(email);
            return user;
        }
        return Optional.empty();
    }
}
