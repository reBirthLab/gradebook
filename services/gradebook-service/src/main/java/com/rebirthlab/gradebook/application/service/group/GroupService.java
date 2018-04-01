package com.rebirthlab.gradebook.application.service.group;

import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.group.Group;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface GroupService {

    Optional<Group> create(GroupDTO groupDTO);

    Optional<Group> updateById(Long id, GroupDTO groupDTO);

    Optional<Group> findById(Long id);

    List<Group> findAll();

    List<Group> findAllByFaculty(Faculty faculty);

    void delete(Long id);

}
