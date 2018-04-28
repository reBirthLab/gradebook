package com.rebirthlab.gradebook.domain.model.gradebook;

import com.rebirthlab.gradebook.domain.shared.ReadOnlyRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Anastasiy
 */
public interface LecturerGradebooksRepository extends ReadOnlyRepository<LecturerGradebooks, Long> {

    @Query("SELECT a FROM LecturerGradebooks a WHERE a.lecturerId = :lecturerId")
    List<LecturerGradebooks> searchBy(@Param("lecturerId") Long lecturerId);

}
