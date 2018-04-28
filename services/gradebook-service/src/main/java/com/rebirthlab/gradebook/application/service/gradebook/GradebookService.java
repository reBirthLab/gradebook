package com.rebirthlab.gradebook.application.service.gradebook;

import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import java.util.List;
import java.util.Optional;

/**
 * Created by Anastasiy
 */
public interface GradebookService {

    Optional<Gradebook> create(GradebookDTO gradebookDTO);

    Optional<Gradebook> updateById(Long id, GradebookDTO gradebookDTO);

    Optional<Gradebook> findById(Long id);

    List<Gradebook> findAll();

    void delete(Long id);
}
