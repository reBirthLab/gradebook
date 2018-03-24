package com.rebirthlab.gradebook.domain.model.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

}
