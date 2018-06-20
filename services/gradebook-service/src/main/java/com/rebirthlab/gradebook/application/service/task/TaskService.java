package com.rebirthlab.gradebook.application.service.task;

import com.rebirthlab.gradebook.domain.model.task.Task;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface TaskService {

    Optional<Task> create(TaskDTO taskDTO);

    Optional<Task> updateById(Long id, TaskDTO taskDTO);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    void delete(Long id);

}
