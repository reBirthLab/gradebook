package com.rebirthlab.gradebook.application.service.user;

import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.group.GroupRepository;
import com.rebirthlab.gradebook.domain.model.user.Student;
import com.rebirthlab.gradebook.domain.model.user.StudentRepository;
import com.rebirthlab.gradebook.domain.model.user.User;
import com.rebirthlab.gradebook.domain.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           StudentRepository studentRepository,
                           GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public Object registerUser(AbstractUserDTO userDTO) {
        User user = new User(userDTO.getEmail(), userDTO.getPassword());
        User createdUser = userRepository.save(user);

        if (userDTO instanceof StudentUserDTO) {
            StudentUserDTO studentDTO = (StudentUserDTO) userDTO;
            Group group = groupRepository.findByNumber(studentDTO.getGroupNumber());
            Student student = new Student();

            student.setUserEmail(studentDTO.getEmail());
            student.setFirstName(studentDTO.getFirstName());
            student.setUserEmail(studentDTO.getLastName());
            student.setGroupId(group);

            Student createdStudent = studentRepository.save(student);
            return createdStudent;
        }
        return null;
    }

}
