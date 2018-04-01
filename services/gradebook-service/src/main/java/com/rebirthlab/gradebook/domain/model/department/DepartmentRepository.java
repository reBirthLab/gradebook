package com.rebirthlab.gradebook.domain.model.department;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    boolean existsByName(String name);
}
