package com.rebirthlab.gradebook.application.service.lecturer;

import com.rebirthlab.gradebook.application.service.user.AbstractUserServiceImpl;
import com.rebirthlab.gradebook.domain.model.department.Department;
import com.rebirthlab.gradebook.domain.model.department.DepartmentRepository;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import com.rebirthlab.gradebook.domain.model.user.LecturerRepository;
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
public class LecturerServiceImpl extends AbstractUserServiceImpl implements LecturerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerServiceImpl.class);

    private LecturerRepository lecturerRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository,
                               DepartmentRepository departmentRepository) {
        this.lecturerRepository = lecturerRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Optional<Lecturer> register(LecturerDTO lecturerDTO) {
        if (lecturerRepository.existsByEmail(lecturerDTO.getEmail())) {
            LOGGER.warn("Cannot register lecturer. Lecturer account '{}' already exists", lecturerDTO.getEmail());
            return Optional.empty();
        }
        Optional<Lecturer> newLecturer = mapNewEntity(lecturerDTO);
        return newLecturer.map(lecture -> lecturerRepository.save(lecture));
    }

    @Override
    @Transactional
    public List<Lecturer> registerAll(List<LecturerDTO> lecturerDTOList) {
        Set<Lecturer> registeredLecturers = lecturerDTOList.stream()
                .map(lecturerDTO -> mapNewEntity(lecturerDTO).orElse(null))
                .filter(Objects::nonNull)
                .filter(lecturer -> {
                    if (lecturerRepository.existsByEmail(lecturer.getEmail())) {
                        LOGGER.warn("Cannot register lecturer. Lecturer account '{}' already exists",
                                lecturer.getEmail());
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toSet());
        List<Lecturer> lecturers = new ArrayList<>();
        lecturerRepository.saveAll(registeredLecturers).forEach(lecturers::add);
        return lecturers;
    }

    @Override
    @Transactional
    public Optional<Lecturer> updateById(Long id, LecturerDTO lecturerDTO) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(id);
        if (!lecturer.isPresent()) {
            LOGGER.warn("Cannot update lecturer. Lecturer id '{}' is not found", id);
            return Optional.empty();
        }
        return updateLecturer(lecturerDTO, lecturer.get());
    }

    @Override
    @Transactional
    public Optional<Lecturer> updateByEmail(String email, LecturerDTO lecturerDTO) {
        Optional<Lecturer> lecturer = lecturerRepository.findByEmail(email);
        if (!lecturer.isPresent()) {
            LOGGER.warn("Cannot update lecturer. Lecturer email '{}' is not found", email);
            return Optional.empty();
        }
        return updateLecturer(lecturerDTO, lecturer.get());
    }

    @Override
    public Optional<Lecturer> findById(Long id) {
        return lecturerRepository.findById(id);
    }

    @Override
    public Optional<Lecturer> findByEmail(String email) {
        return lecturerRepository.findByEmail(email);
    }

    @Override
    public Optional<List<Lecturer>> findAll() {
        List<Lecturer> lecturers = new ArrayList<>();
        lecturerRepository.findAll().forEach(lecturers::add);
        return lecturers.isEmpty() ? Optional.empty() : Optional.of(lecturers);
    }

    @Override
    public Optional<List<Lecturer>> findAllByDepartmentId(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (!department.isPresent()) {
            LOGGER.warn("Cannot complete operation. Department id '{}' doesn't exist", departmentId);
            return Optional.empty();
        }
        List<Lecturer> lecturers = lecturerRepository.findAllByDepartmentId(department.get());
        return lecturers.isEmpty() ? Optional.empty() : Optional.of(lecturers);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        lecturerRepository.deleteById(id);
        LOGGER.info("Lecturer with id '{}' was successfully deleted", id);
    }

    private Optional<Lecturer> mapNewEntity(LecturerDTO lecturerDTO) {
        Optional<Department> department = departmentRepository.findById(lecturerDTO.getDepartmentId());
        if (!department.isPresent()) {
            LOGGER.warn("Cannot register lecturer. Department id '{}' doesn't exist", lecturerDTO.getDepartmentId());
            return Optional.empty();
        }
        Lecturer newLecturer = new Lecturer(lecturerDTO.getEmail(),
                lecturerDTO.getFirstName(),
                lecturerDTO.getLastName(),
                department.get());
        return Optional.of(newLecturer);
    }

    private Optional<Lecturer> updateLecturer(LecturerDTO lecturerDTO, Lecturer lecturer) {
        patchLecturerDTO(lecturerDTO, lecturer);
        Optional<Lecturer> updatedLecturer = mapUpdatedEntity(lecturerDTO);
        return updatedLecturer.map(l -> lecturerRepository.save(l));
    }

    private void patchLecturerDTO(LecturerDTO lecturerDTO, Lecturer lecturer) {
        patchAbstractUserDTO(lecturerDTO, lecturer);
        if (lecturerDTO.getDepartmentId() == null) {
            lecturerDTO.setDepartmentId(lecturer.getDepartmentId().getId());
        }
    }

    private Optional<Lecturer> mapUpdatedEntity(LecturerDTO lecturerDTO) {
        Optional<Department> department = departmentRepository.findById(lecturerDTO.getDepartmentId());
        if (!department.isPresent()) {
            LOGGER.warn("Cannot update lecturer. Department id '{}' doesn't exist", lecturerDTO.getDepartmentId());
            return Optional.empty();
        }
        Lecturer updatedLecturer = new Lecturer(lecturerDTO.getId(),
                lecturerDTO.getEmail(),
                lecturerDTO.getFirstName(),
                lecturerDTO.getLastName(),
                department.get());
        return Optional.of(updatedLecturer);
    }
}
