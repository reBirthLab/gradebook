package com.rebirthlab.gradebook.application.service.student;

import com.rebirthlab.gradebook.domain.model.user.Student;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface StudentService {

    Student register(StudentDTO studentDTO);

    List<Student> registerAll(List<StudentDTO> studentDTOList);

    Optional<Student> updateById(Long id, StudentDTO studentDTO);

    // For user's own account update
    Optional<Student> updateByEmail(String email, StudentDTO studentDTO);

    Optional<Student> findById(Long id);

    Optional<Student> findByEmail(String email);

    Optional<List<Student>> findAll();

    void delete(Long id);

}
