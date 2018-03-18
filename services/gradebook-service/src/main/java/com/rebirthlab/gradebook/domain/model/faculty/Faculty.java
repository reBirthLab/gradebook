package com.rebirthlab.gradebook.domain.model.faculty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.department.Department;
import com.rebirthlab.gradebook.domain.model.group.Group;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    @NotNull
    @Size(min = 1, max = 150)
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyId")
    private Collection<Department> departmentCollection;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyId")
    private Collection<Group> academicGroupCollection;

    public Faculty() {
    }

    public Faculty(String name) {
        this.name = name;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }

    public Collection<Department> getDepartmentCollection() {
        return new HashSet<>(departmentCollection);
    }

    public Collection<Group> getAcademicGroupCollection() {
        return new HashSet<>(academicGroupCollection);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facultyId != null ? facultyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Faculty)) {
            return false;
        }
        Faculty other = (Faculty) object;
        if ((this.facultyId == null && other.facultyId != null) || (this.facultyId != null && !this.facultyId
                .equals(other.facultyId))) {
            return false;
        }
        return true;
    }

}
