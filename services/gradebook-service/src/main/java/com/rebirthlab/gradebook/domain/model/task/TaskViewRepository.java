package com.rebirthlab.gradebook.domain.model.task;

import com.rebirthlab.gradebook.domain.shared.ReadOnlyRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Anastasiy
 */
public interface TaskViewRepository extends ReadOnlyRepository<TaskView, Long> {

    @Query("SELECT a FROM TaskView a WHERE a.groupId = :groupId AND " +
            "a.semesterId = :semesterId AND a.gradebookId = :gradebookId")
    List<TaskView> searchBy(@Param("groupId") Long groupId,
                            @Param("semesterId") Long semesterId,
                            @Param("gradebookId") Long gradebookId);

    @Query("SELECT a FROM TaskView a WHERE a.groupId = :groupId AND " +
            "a.semesterId = :semesterId AND a.gradebookId = :gradebookId AND a.studentId = :studentId")
    List<TaskView> searchBy(@Param("groupId") Long groupId,
                            @Param("semesterId") Long semesterId,
                            @Param("gradebookId") Long gradebookId,
                            @Param("studentId") Long studentId);
}
