package com.rebirthlab.gradebook.domain.model.group;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByNumber(String number);

}
