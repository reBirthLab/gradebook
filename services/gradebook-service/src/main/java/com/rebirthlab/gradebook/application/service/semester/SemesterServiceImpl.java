package com.rebirthlab.gradebook.application.service.semester;

import com.rebirthlab.gradebook.domain.model.semester.Semester;
import com.rebirthlab.gradebook.domain.model.semester.SemesterRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class SemesterServiceImpl implements SemesterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SemesterServiceImpl.class);

    private SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    @Transactional
    public Optional<Semester> create(SemesterDTO semesterDTO) {
        if (semesterRepository.existsBy(semesterDTO.getName(), semesterDTO.getAcademicYear())) {
            LOGGER.warn("Cannot create semester. Semester '{} {}' already exists",
                    semesterDTO.getName(), semesterDTO.getAcademicYear());
            return Optional.empty();
        }
        Semester newSemester = new Semester(semesterDTO.getName(), semesterDTO.getAcademicYear());
        return Optional.of(semesterRepository.save(newSemester));
    }

    @Override
    @Transactional
    public Optional<Semester> updateById(Long id, SemesterDTO semesterDTO) {
        Optional<Semester> semester = semesterRepository.findById(id);
        if (!semester.isPresent()) {
            LOGGER.warn("Cannot update semester. Semester id '{}' is not found", id);
            return Optional.empty();
        }
        return updateSemester(semesterDTO, semester.get());
    }

    @Override
    public Optional<Semester> findById(Long id) {
        return semesterRepository.findById(id);
    }

    @Override
    public List<Semester> findAll() {
        List<Semester> semesters = new ArrayList<>();
        semesterRepository.findAll().forEach(semesters::add);
        return semesters;
    }

    @Override
    public List<Semester> findActual() {
        int currentYear = LocalDate.now().getYear();
        return semesterRepository.findActual(currentYear);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        semesterRepository.deleteById(id);
        LOGGER.info("Semester with id '{}' was successfully deleted", id);
    }

    private Optional<Semester> updateSemester(SemesterDTO semesterDTO, Semester semester) {
        patchSemesterDTO(semesterDTO, semester);
        Semester updatedSemester = new Semester(semesterDTO.getId(),
                semesterDTO.getName(),
                semesterDTO.getAcademicYear());
        return Optional.of(semesterRepository.save(updatedSemester));
    }

    private void patchSemesterDTO(SemesterDTO semesterDTO, Semester semester) {
        semesterDTO.setId(semester.getId());
        if (semesterDTO.getName() == null) {
            semesterDTO.setName(semester.getName());
        }
        if (semesterDTO.getAcademicYear() == null) {
            semesterDTO.setAcademicYear(semester.getAcademicYear());
        }
    }
}
