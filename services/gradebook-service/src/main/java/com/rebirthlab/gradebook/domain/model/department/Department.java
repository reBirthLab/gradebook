package com.rebirthlab.gradebook.domain.model.department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    private String name;

    @JoinColumn(name = "faculty_id")
    @ManyToOne(optional = false)
    private Faculty facultyId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private Collection<Lecturer> lecturerCollection;

    public Department() {
    }

    public Department(Long id, String name, Faculty facultyId) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
    }

    public Department(String name, Faculty facultyId) {
        this.name = name;
        this.facultyId = facultyId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Faculty getFacultyId() {
        return facultyId;
    }

    public Collection<Lecturer> getLecturerCollection() {
        return new HashSet<>(lecturerCollection);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id
                .equals(other.id))) {
            return false;
        }
        return true;
    }

}
