package com.rebirthlab.gradebook.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.department.Department;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Lecturer extends User {

    @JoinColumn(name = "department_id")
    @ManyToOne(optional = false)
    private Department departmentId;

    @JsonIgnore
    @ManyToMany(mappedBy = "lecturerCollection")
    private Collection<Gradebook> gradebookCollection;

    public Lecturer() {
        super(GradebookConstants.ROLE_LECTURER);
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public Collection<Gradebook> getGradebookCollection() {
        return new HashSet<>(gradebookCollection);
    }
}
