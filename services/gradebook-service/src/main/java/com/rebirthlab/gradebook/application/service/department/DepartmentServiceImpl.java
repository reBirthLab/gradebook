package com.rebirthlab.gradebook.application.service.department;

import com.rebirthlab.gradebook.domain.model.department.Department;
import com.rebirthlab.gradebook.domain.model.department.DepartmentRepository;
import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.faculty.FacultyRepository;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private DepartmentRepository departmentRepository;
    private FacultyRepository facultyRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 FacultyRepository facultyRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional
    public Optional<Department> create(DepartmentDTO departmentDTO) {
        if (departmentRepository.existsByName(departmentDTO.getName())) {
            LOGGER.info("Cannot create department. Department '{}' already exists", departmentDTO.getName());
            return Optional.empty();
        }
        Optional<Department> newDepartment = mapNewEntity(departmentDTO);
        return newDepartment.map(department -> departmentRepository.save(department));
    }

    @Override
    @Transactional
    public Optional<Department> updateById(Long id, DepartmentDTO departmentDTO) {
        Optional<Department> department = departmentRepository.findById(id);
        if (!department.isPresent()) {
            LOGGER.info("Cannot update department. Department id '{}' is not found", id);
            return Optional.empty();
        }
        return updateDepartment(departmentDTO, department.get());
    }

    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(departments::add);
        return departments;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
        LOGGER.info("Department with id '{}' was successfully deleted", id);
    }

    private Optional<Department> mapNewEntity(DepartmentDTO departmentDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(departmentDTO.getFacultyId());
        if (!faculty.isPresent()) {
            LOGGER.info("Cannot create department. Faculty id '{}' doesn't exist", departmentDTO.getFacultyId());
            return Optional.empty();
        }
        Department department = new Department(departmentDTO.getName(), faculty.get());
        return Optional.of(department);
    }

    private Optional<Department> updateDepartment(DepartmentDTO departmentDTO, Department department) {
        patchDepartmentDTO(departmentDTO, department);
        Optional<Department> updatedDepartment = mapUpdatedEntity(departmentDTO);
        if (!updatedDepartment.isPresent()) {
            return Optional.empty();
        }
        Department savedDepartment = departmentRepository.save(updatedDepartment.get());
        return Optional.of(savedDepartment);
    }

    private void patchDepartmentDTO(DepartmentDTO departmentDTO, Department department) {
        departmentDTO.setId(department.getId());
        if (departmentDTO.getName() == null) {
            departmentDTO.setName(department.getName());
        }
        if (departmentDTO.getFacultyId() == null) {
            departmentDTO.setFacultyId(department.getFacultyId().getId());
        }
    }

    private Optional<Department> mapUpdatedEntity(DepartmentDTO departmentDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(departmentDTO.getFacultyId());
        if (!faculty.isPresent()) {
            LOGGER.info("Cannot update department. Faculty id '{}' doesn't exist", departmentDTO.getFacultyId());
            return Optional.empty();
        }
        Department updatedDepartment = new Department(departmentDTO.getId(),
                departmentDTO.getName(),
                faculty.get());
        return Optional.of(updatedDepartment);
    }
}
