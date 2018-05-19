package com.rebirthlab.gradebook.domain.model.gradebook;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Anastasiy
 */
public interface GradebookRepository extends CrudRepository<Gradebook, Long> {

    @Query("SELECT CASE WHEN (COUNT(a) > 0) THEN true ELSE false END " +
            "FROM Gradebook a WHERE a.groupId.id = :groupId AND " +
            "a.semesterId.id = :semesterId AND a.subject = :subject")
    boolean existsBy(@Param("groupId") Long groupId, @Param("semesterId") Long semesterId,
                     @Param("subject") String subject);

}
