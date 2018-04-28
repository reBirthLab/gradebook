package com.rebirthlab.gradebook.domain.model.gradebook;

import com.rebirthlab.gradebook.domain.shared.ReadOnlyRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Anastasiy
 */
public interface StudentGradebooksRepository extends ReadOnlyRepository<StudentGradebooks, Long> {

    @Query("SELECT a FROM StudentGradebooks a WHERE a.studentId = :studentId")
    List<StudentGradebooks> searchBy(@Param("studentId") Long studentId);

}
