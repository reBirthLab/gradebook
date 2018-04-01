package com.rebirthlab.gradebook.domain.model.faculty;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface FacultyRepository extends CrudRepository<Faculty, Long> {

    boolean existsByName(String name);
}
