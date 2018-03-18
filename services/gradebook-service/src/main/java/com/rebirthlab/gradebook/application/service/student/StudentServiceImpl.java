package com.rebirthlab.gradebook.application.service.student;

import com.rebirthlab.gradebook.domain.model.user.Student;
import com.rebirthlab.gradebook.domain.model.user.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final static Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student register(StudentDTO studentDTO) {
        return null;
    }

    @Override
    @Transactional
    public List<Student> registerAll(List<StudentDTO> studentDTOList) {
        return null;
    }

    @Override
    @Transactional
    public Optional<Student> updateById(Long id, StudentDTO studentDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> updateByEmail(String email, StudentDTO studentDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public Optional<List<Student>> findAll() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(id);
        LOGGER.info("Student user with id={} was successfully deleted", id);
    }
}
