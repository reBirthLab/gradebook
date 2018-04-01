package com.rebirthlab.gradebook.domain.model.group;

import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anastasiy
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByNumber(String number);

    List<Group> findByFacultyId(Faculty facultyId);

    boolean existsByNumber(String number);
}
