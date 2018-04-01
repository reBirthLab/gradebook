package com.rebirthlab.gradebook.application.service.department;

import com.rebirthlab.gradebook.domain.model.department.Department;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface DepartmentService {

    Optional<Department> create(DepartmentDTO departmentDTO);

    Optional<Department> updateById(Long id, DepartmentDTO departmentDTO);

    Optional<Department> findById(Long id);

    List<Department> findAll();

    void delete(Long id);

}
