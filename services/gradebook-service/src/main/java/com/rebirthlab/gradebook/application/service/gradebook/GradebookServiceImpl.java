package com.rebirthlab.gradebook.application.service.gradebook;

import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.gradebook.GradebookRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Anastasiy
 */
@Service
public class GradebookServiceImpl implements GradebookService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GradebookServiceImpl.class);

    private GradebookRepository gradebookRepository;

    @Autowired
    public GradebookServiceImpl(GradebookRepository gradebookRepository) {
        this.gradebookRepository = gradebookRepository;
    }

    @Override
    public Optional<Gradebook> create(GradebookDTO gradebookDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<Gradebook> updateById(Long id, GradebookDTO gradebookDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<Gradebook> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Gradebook> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
