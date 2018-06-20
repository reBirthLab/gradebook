package com.rebirthlab.gradebook.domain.model.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Anastasiy
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("SELECT CASE WHEN (COUNT(a) > 0) THEN true ELSE false END " +
            "FROM Task a WHERE a.gradebookId.id = :gradebookId AND a.title = :title")
    boolean existsBy(@Param("gradebookId") Long gradebookId, @Param("title") String title);
}
