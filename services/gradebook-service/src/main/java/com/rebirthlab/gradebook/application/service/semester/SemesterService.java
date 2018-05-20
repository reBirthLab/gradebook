package com.rebirthlab.gradebook.application.service.semester;

import com.rebirthlab.gradebook.domain.model.semester.Semester;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface SemesterService {

    Optional<Semester> create(SemesterDTO semesterDTO);

    Optional<Semester> updateById(Long id, SemesterDTO semesterDTO);

    Optional<Semester> findById(Long id);

    List<Semester> findAll();

    List<Semester> findActual();

    void delete(Long id);

}
