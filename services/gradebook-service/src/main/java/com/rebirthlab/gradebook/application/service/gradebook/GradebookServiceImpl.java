package com.rebirthlab.gradebook.application.service.gradebook;

import com.rebirthlab.gradebook.application.util.Actions;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.gradebook.GradebookRepository;
import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.group.GroupRepository;
import com.rebirthlab.gradebook.domain.model.semester.Semester;
import com.rebirthlab.gradebook.domain.model.semester.SemesterRepository;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import com.rebirthlab.gradebook.domain.model.user.LecturerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GradebookServiceImpl.class);
    private GradebookRepository gradebookRepository;
    private GroupRepository groupRepository;
    private SemesterRepository semesterRepository;
    private LecturerRepository lecturerRepository;

    @Autowired
    public GradebookServiceImpl(GradebookRepository gradebookRepository, GroupRepository groupRepository,
                                SemesterRepository semesterRepository, LecturerRepository lecturerRepository) {
        this.gradebookRepository = gradebookRepository;
        this.groupRepository = groupRepository;
        this.semesterRepository = semesterRepository;
        this.lecturerRepository = lecturerRepository;
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
        Actions action = Actions.CREATE;
        Optional<Group> group = getGroup(gradebookDTO, action);
        Optional<Semester> semester = getSemester(gradebookDTO, action);
        Optional<Set<Lecturer>> lecturers = getLecturers(gradebookDTO, action);
        if (!group.isPresent() || !semester.isPresent() || !lecturers.isPresent()) {
            return Optional.empty();
        }
        Gradebook gradebook = new Gradebook(group.get(), semester.get(), gradebookDTO.getSubject(),
                gradebookDTO.getDescription(), lecturers.get());
        return Optional.of(gradebook);
    }

    private Optional<Gradebook> mapUpdatedEntity(GradebookDTO gradebookDTO) {
        Actions action = Actions.UPDATE;
        Optional<Group> group = getGroup(gradebookDTO, action);
        Optional<Semester> semester = getSemester(gradebookDTO, action);
        Optional<Set<Lecturer>> lecturers = getLecturers(gradebookDTO, action);
        if (!group.isPresent() || !semester.isPresent() || !lecturers.isPresent()) {
            return Optional.empty();
        }
        Gradebook updatedGradebook = new Gradebook(gradebookDTO.getId(), group.get(), semester.get(),
                gradebookDTO.getSubject(), gradebookDTO.getDescription(), lecturers.get());
        return Optional.of(updatedGradebook);
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

    private Optional<Group> getGroup(GradebookDTO gradebookDTO, Actions action) {
        return groupRepository.findById(gradebookDTO.getGroupId()).or(() -> {
            LOGGER.warn("Cannot {} gradebook. Group id '{}' doesn't exist", action, gradebookDTO.getGroupId());
            return Optional.empty();
        });
    }

    private Optional<Semester> getSemester(GradebookDTO gradebookDTO, Actions action) {
        return semesterRepository.findById(gradebookDTO.getSemesterId()).or(() -> {
            LOGGER.warn("Cannot {} gradebook. Semester id '{}' doesn't exist", action, gradebookDTO.getSemesterId());
            return Optional.empty();
        });
    }

    private Optional<Set<Lecturer>> getLecturers(GradebookDTO gradebookDTO, Actions action) {
        Set<Long> lecturerIds = gradebookDTO.getLecturerCollection();
        Set<Lecturer> lecturers = lecturerIds.stream()
                .map(lecturerId -> lecturerRepository.findById(lecturerId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if (lecturers.size() != lecturerIds.size()) {
            LOGGER.warn("Cannot {} gradebook. Cannot find lecturer id(s) {}", action, lecturerIds);
            return Optional.empty();
        }
        return Optional.of(lecturers);
    }

}
