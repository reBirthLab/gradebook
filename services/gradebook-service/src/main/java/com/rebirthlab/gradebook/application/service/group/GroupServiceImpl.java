package com.rebirthlab.gradebook.application.service.group;

import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.faculty.FacultyRepository;
import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.group.GroupRepository;
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
public class GroupServiceImpl implements GroupService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    private GroupRepository groupRepository;
    private FacultyRepository facultyRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            FacultyRepository facultyRepository) {
        this.groupRepository = groupRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional
    public Optional<Group> create(GroupDTO groupDTO) {
        if (groupRepository.existsByNumber(groupDTO.getNumber())) {
            LOGGER.info("Cannot create group. Group '{}' already exists", groupDTO.getNumber());
            return Optional.empty();
        }
        Optional<Group> newGroup = mapNewEntity(groupDTO);
        return newGroup.map(group -> groupRepository.save(group));
    }

    @Override
    @Transactional
    public Optional<Group> updateById(Long id, GroupDTO groupDTO) {
        Optional<Group> group = groupRepository.findById(id);
        if (!group.isPresent()) {
            LOGGER.info("Cannot update group. Group id '{}' is not found", id);
            return Optional.empty();
        }
        return updateDepartment(groupDTO, group.get());
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @Override
    public List<Group> findAllByFaculty(Faculty faculty) {
        return groupRepository.findByFacultyId(faculty);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        groupRepository.deleteById(id);
        LOGGER.info("Group with id '{}' was successfully deleted", id);
    }

    private Optional<Group> mapNewEntity(GroupDTO groupDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(groupDTO.getFacultyId());
        if (!faculty.isPresent()) {
            LOGGER.info("Cannot create group. Faculty id '{}' doesn't exist", groupDTO.getFacultyId());
            return Optional.empty();
        }
        Group group = new Group(groupDTO.getNumber(), faculty.get());
        return Optional.of(group);
    }

    private Optional<Group> updateDepartment(GroupDTO groupDTO, Group group) {
        patchGroupDTO(groupDTO, group);
        Optional<Group> updatedGroup = mapUpdatedEntity(groupDTO);
        return updatedGroup.map(g -> groupRepository.save(g));
    }

    private void patchGroupDTO(GroupDTO groupDTO, Group group) {
        groupDTO.setId(group.getId());
        if (groupDTO.getNumber() == null) {
            groupDTO.setNumber(group.getNumber());
        }
        if (groupDTO.getFacultyId() == null) {
            groupDTO.setFacultyId(group.getFacultyId().getId());
        }
    }

    private Optional<Group> mapUpdatedEntity(GroupDTO groupDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(groupDTO.getFacultyId());
        if (!faculty.isPresent()) {
            LOGGER.info("Cannot update group. Faculty id '{}' doesn't exist", groupDTO.getFacultyId());
            return Optional.empty();
        }
        Group updatedGroup = new Group(groupDTO.getId(),
                groupDTO.getNumber(),
                faculty.get());
        return Optional.of(updatedGroup);
    }
}
