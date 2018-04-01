package com.rebirthlab.gradebook.application.service.faculty;

import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface FacultyService {

    Optional<Faculty> create(FacultyDTO facultyDTO);

    Optional<Faculty> updateById(Long id, FacultyDTO facultyDTO);

    Optional<Faculty> findById(Long id);

    List<Faculty> findAll();

    void delete(Long id);
}
