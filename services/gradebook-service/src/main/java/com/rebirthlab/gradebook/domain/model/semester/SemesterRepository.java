package com.rebirthlab.gradebook.domain.model.semester;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Anastasiy
 */
public interface SemesterRepository extends CrudRepository<Semester, Long> {

    @Query("SELECT a FROM Semester a WHERE a.name = :name AND a.academicYear = :academicYear")
    boolean existsBy(@Param("name") String name, @Param("academicYear") short academicYear);

    @Query("SELECT a FROM Semester a WHERE a.academicYear = :currentYear OR a.academicYear = :currentYear + 1")
    List<Semester> findActual(@Param("currentYear") int currentYear);
}
