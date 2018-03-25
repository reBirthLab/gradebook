package com.rebirthlab.gradebook.application.service.lecturer;

import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface LecturerService {

    Optional<Lecturer> register(LecturerDTO lecturerDTO);

    List<Lecturer> registerAll(List<LecturerDTO> lecturerDTOList);

    Optional<Lecturer> updateById(Long id, LecturerDTO lecturerDTO);

    Optional<Lecturer> updateByEmail(String email, LecturerDTO lecturerDTO);

    Optional<Lecturer> findById(Long id);

    Optional<Lecturer> findByEmail(String email);

    Optional<List<Lecturer>> findAll();

    Optional<List<Lecturer>> findAllByDepartmentId(Long departmentId);

    void delete(Long id);

}