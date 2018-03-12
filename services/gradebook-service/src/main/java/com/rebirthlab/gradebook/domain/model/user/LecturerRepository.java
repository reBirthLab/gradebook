package com.rebirthlab.gradebook.domain.model.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface LecturerRepository extends CrudRepository<Lecturer, Long> {

    Optional<Lecturer> findByEmail(String email);
}
