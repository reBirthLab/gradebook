package com.rebirthlab.gradebook.application.service.gradebook;

import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.gradebook.GradebookRepository;
import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.group.GroupRepository;
import com.rebirthlab.gradebook.domain.model.semester.Semester;
import com.rebirthlab.gradebook.domain.model.semester.SemesterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
@Service
public class GradebookServiceImpl implements GradebookService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GradebookServiceImpl.class);

    private GradebookRepository gradebookRepository;
    private GroupRepository groupRepository;
    private SemesterRepository semesterRepository;

    @Autowired
    public GradebookServiceImpl(GradebookRepository gradebookRepository, GroupRepository groupRepository,
                                SemesterRepository semesterRepository) {
        this.gradebookRepository = gradebookRepository;
        this.groupRepository = groupRepository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    @Transactional
    public Optional<Gradebook> create(GradebookDTO gradebookDTO) {
        if (gradebookRepository.existsBy(gradebookDTO.getGroupId(), gradebookDTO.getSemesterId(),
                gradebookDTO.getSubject())) {
            LOGGER.warn("Cannot create gradebook. Gradebook with subject '{}' for groupId={} " +
                            "and semesterId={} already exists",
                    gradebookDTO.getSubject(), gradebookDTO.getGroupId(), gradebookDTO.getSemesterId());
            return Optional.empty();
        }
        Optional<Gradebook> newGradebook = mapNewEntity(gradebookDTO);
        return newGradebook.map(gradebook -> gradebookRepository.save(gradebook));
    }

    @Override
    @Transactional
    public Optional<Gradebook> updateById(Long id, GradebookDTO gradebookDTO) {
        Optional<Gradebook> gradebook = gradebookRepository.findById(id);
        if (!gradebook.isPresent()) {
            LOGGER.warn("Cannot update gradebook. Gradebook id '{}' is not found", id);
            return Optional.empty();
        }
        return updateGradebook(gradebookDTO, gradebook.get());
    }

    @Override
    public Optional<Gradebook> findById(Long id) {
        return gradebookRepository.findById(id);
    }

    @Override
    public List<Gradebook> findAll() {
        List<Gradebook> gradebooks = new ArrayList<>();
        gradebookRepository.findAll().forEach(gradebooks::add);
        return gradebooks;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        gradebookRepository.deleteById(id);
        LOGGER.info("Gradebook with id '{}' was successfully deleted", id);
    }

    private Optional<Gradebook> mapNewEntity(GradebookDTO gradebookDTO) {
        Optional<Group> group = groupRepository.findById(gradebookDTO.getGroupId());
        if (!group.isPresent()) {
            LOGGER.warn("Cannot create gradebook. Group id '{}' doesn't exist", gradebookDTO.getGroupId());
            return Optional.empty();
        }
        Optional<Semester> semester = semesterRepository.findById(gradebookDTO.getSemesterId());
        if (!semester.isPresent()) {
            LOGGER.warn("Cannot create gradebook. Semester id '{}' doesn't exist", gradebookDTO.getSemesterId());
            return Optional.empty();
        }
        Gradebook gradebook = new Gradebook(group.get(), semester.get(), gradebookDTO.getSubject(),
                gradebookDTO.getDescription());
        return Optional.of(gradebook);
    }

    private Optional<Gradebook> updateGradebook(GradebookDTO gradebookDTO, Gradebook gradebook) {
        patchGradebookDTO(gradebookDTO, gradebook);
        Optional<Gradebook> updatedGradebook = mapUpdatedEntity(gradebookDTO);
        return updatedGradebook.map(g -> gradebookRepository.save(g));
    }

    private void patchGradebookDTO(GradebookDTO gradebookDTO, Gradebook gradebook) {
        gradebookDTO.setId(gradebook.getId());
        if (gradebookDTO.getGroupId() == null) {
            gradebookDTO.setGroupId(gradebook.getGroupId().getId());
        }
        if (gradebookDTO.getSemesterId() == null) {
            gradebookDTO.setSemesterId(gradebook.getSemesterId().getId());
        }
        if (gradebookDTO.getSubject() == null) {
            gradebookDTO.setSubject(gradebook.getSubject());
        }
        if (gradebookDTO.getDescription() == null) {
            gradebookDTO.setDescription(gradebook.getDescription());
        }
    }

    private Optional<Gradebook> mapUpdatedEntity(GradebookDTO gradebookDTO) {
        Optional<Group> group = groupRepository.findById(gradebookDTO.getGroupId());
        if (!group.isPresent()) {
            LOGGER.warn("Cannot update gradebook. Group id '{}' doesn't exist", gradebookDTO.getGroupId());
            return Optional.empty();
        }
        Optional<Semester> semester = semesterRepository.findById(gradebookDTO.getSemesterId());
        if (!semester.isPresent()) {
            LOGGER.warn("Cannot update gradebook. Semester id '{}' doesn't exist", gradebookDTO.getSemesterId());
            return Optional.empty();
        }
        Gradebook updatedGradebook = new Gradebook(gradebookDTO.getId(), group.get(), semester.get(),
                gradebookDTO.getSubject(), gradebookDTO.getDescription());
        return Optional.of(updatedGradebook);
    }
}
