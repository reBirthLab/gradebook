package com.rebirthlab.gradebook.domain.model.group;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Group findByNumber(String number);

}
