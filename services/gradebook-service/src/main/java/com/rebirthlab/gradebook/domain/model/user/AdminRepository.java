package com.rebirthlab.gradebook.domain.model.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface AdminRepository extends CrudRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);
}
