package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.model.Department;
import com.rebirthlab.gradebook.domain.model.Gradebook;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Lecturer extends User {

    @ManyToMany(mappedBy = "lecturerCollection")
    private Collection<Gradebook> gradebookCollection;

    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne(optional = false)
    private Department departmentId;

    public Lecturer() {
        super(GradebookConstants.ROLE_LECTURER);
    }

    public Collection<Gradebook> getGradebookCollection() {
        return gradebookCollection;
    }

    public Department getDepartmentId() {
        return departmentId;
    }
}
