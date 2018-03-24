package com.rebirthlab.gradebook.application.service.student;

import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.group.GroupRepository;
import com.rebirthlab.gradebook.domain.model.user.Student;
import com.rebirthlab.gradebook.domain.model.user.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
    private GroupRepository groupRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public Optional<Student> register(StudentDTO studentDTO) {
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            LOGGER.info("Cannot register user. User account {} already exists", studentDTO.getEmail());
            return Optional.empty();
        }
        Optional<Student> newStudent = mapToNewEntity(studentDTO);
        return newStudent.map(student -> studentRepository.save(student));
    }

    @Override
    @Transactional
    public List<Student> registerAll(List<StudentDTO> studentDTOList) {
        Set<Student> registeredStudents = studentDTOList.stream()
                .map(studentDTO -> mapToNewEntity(studentDTO).orElse(null))
                .filter(Objects::nonNull)
                .filter(student -> {
                    if (studentRepository.existsByEmail(student.getEmail())) {
                        LOGGER.info("Cannot register user. User account {} already exists", student.getEmail());
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toSet());
        List<Student> students = new ArrayList<>();
        studentRepository.saveAll(registeredStudents).forEach(students::add);
        return students;
    }

    @Override
    @Transactional
    public Optional<Student> updateById(Long id, StudentDTO studentDTO) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            LOGGER.info("Cannot update user. User id {} is not found", id);
            return Optional.empty();
        }
        return updateStudent(studentDTO, student.get());
    }

    @Override
    public Optional<Student> updateByEmail(String email, StudentDTO studentDTO) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (!student.isPresent()) {
            LOGGER.info("Cannot update user. User email {} is not found", email);
            return Optional.empty();
        }
        return updateStudent(studentDTO, student.get());
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

    private Optional<Student> mapToNewEntity(StudentDTO studentDTO) {
        Optional<Group> group = groupRepository.findById(studentDTO.getGroupId());
        if (!group.isPresent()) {
            LOGGER.info("Cannot register user. Group id {} doesn't exist", studentDTO.getGroupId());
            return Optional.empty();
        }
        Student newStudent = new Student(studentDTO.getEmail(),
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                group.get());
        return Optional.of(newStudent);
    }

    private Optional<Student> updateStudent(StudentDTO studentDTO, Student student) {
        StudentDTO patchedStudentDTO = patchStudentDTO(studentDTO, student);
        Optional<Student> updatedStudent = mapToUpdatedEntity(patchedStudentDTO);
        if (!updatedStudent.isPresent()) {
            return Optional.empty();
        }
        Student savedStudent = studentRepository.save(updatedStudent.get());
        return Optional.of(savedStudent);
    }

    private StudentDTO patchStudentDTO(StudentDTO studentDTO, Student student) {
        studentDTO.setId(student.getId());
        studentDTO.setEmail(student.getEmail());
        if (studentDTO.getFirstName() == null || studentDTO.getFirstName().isEmpty()) {
            studentDTO.setFirstName(student.getFirstName());
        }
        if (studentDTO.getLastName() == null || studentDTO.getLastName().isEmpty()) {
            studentDTO.setLastName(student.getLastName());
        }
        if (studentDTO.getGroupId() == null) {
            studentDTO.setGroupId(student.getGroupId().getId());
        }
        return studentDTO;
    }

    private Optional<Student> mapToUpdatedEntity(StudentDTO studentDTO) {
        Optional<Group> group = groupRepository.findById(studentDTO.getGroupId());
        if (!group.isPresent()) {
            LOGGER.info("Cannot update user. Group id {} doesn't exist", studentDTO.getGroupId());
            return Optional.empty();
        }
        Student updatedStudent = new Student(studentDTO.getId(),
                studentDTO.getEmail(),
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                group.get());
        return Optional.of(updatedStudent);
    }

}
