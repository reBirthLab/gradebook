package com.rebirthlab.gradebook.application.service.user;

import com.rebirthlab.gradebook.domain.model.user.AdminRepository;
import com.rebirthlab.gradebook.domain.model.user.LecturerRepository;
import com.rebirthlab.gradebook.domain.model.user.StudentRepository;
import com.rebirthlab.gradebook.domain.model.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Anastasiy
 */
@Service
public class UserServiceImpl implements UserService {

    private AdminRepository adminRepository;
    private LecturerRepository lecturerRepository;
    private StudentRepository studentRepository;

    @Autowired
    public UserServiceImpl(AdminRepository adminRepository,
                           LecturerRepository lecturerRepository,
                           StudentRepository studentRepository) {
        this.adminRepository = adminRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Set<User> user = new HashSet<>();
        adminRepository.findByEmail(email).ifPresent(user::add);
        lecturerRepository.findByEmail(email).ifPresent(user::add);
        studentRepository.findByEmail(email).ifPresent(user::add);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(user.iterator().next());
    }

    @Override
    public Optional<List<User>> findAllUsers() {
        List<User> users = new ArrayList<>();
        adminRepository.findAll().forEach(users::add);
        lecturerRepository.findAll().forEach(users::add);
        studentRepository.findAll().forEach(users::add);
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }

    @Override
    public Optional<String> getUserRoleByEmail(String email) {
        Optional<User> user = findUserByEmail(email);
        return user.map(User::getRole);
    }

    @Override
    public boolean isUserRole(String email, String... roles) {
        String userRole = getUserRoleByEmail(email)
                .orElseThrow(() -> new BadRequestException("No user account found associated with email " +
                        " [ " + email + " ]"));
        return Arrays.asList(roles).contains(userRole);
    }
}
