package com.rebirthlab.gradebook.application.service.faculty;

import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.faculty.FacultyRepository;
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
public class FacultyServiceImpl implements FacultyService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional
    public Optional<Faculty> create(FacultyDTO facultyDTO) {
        if (facultyRepository.existsByName(facultyDTO.getName())) {
            LOGGER.info("Cannot create faculty. Faculty '{}' already exists", facultyDTO.getName());
            return Optional.empty();
        }
        Faculty newFaculty = new Faculty(facultyDTO.getName());
        return Optional.of(facultyRepository.save(newFaculty));
    }

    @Override
    @Transactional
    public Optional<Faculty> updateById(Long id, FacultyDTO facultyDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (!faculty.isPresent()) {
            LOGGER.info("Cannot update faculty. Faculty id '{}' is not found", id);
            return Optional.empty();
        }
        if (facultyDTO.getName() == null) {
            return faculty;
        }
        Faculty updatedFaculty = new Faculty(id, facultyDTO.getName());
        return Optional.of(facultyRepository.save(updatedFaculty));
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }

    @Override
    public List<Faculty> findAll() {
        List<Faculty> faculties = new ArrayList<>();
        facultyRepository.findAll().forEach(faculties::add);
        return faculties;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        facultyRepository.deleteById(id);
        LOGGER.info("Faculty with id '{}' was successfully deleted", id);
    }
}
