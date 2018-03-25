package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.model.department.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface LecturerRepository extends CrudRepository<Lecturer, Long> {

    Optional<Lecturer> findByEmail(String email);

    List<Lecturer> findAllByDepartmentId(Department departmentId);

    boolean existsByEmail(String email);
}
