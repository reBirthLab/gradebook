package com.rebirthlab.gradebook.domain.model.semester;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
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
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private short academicYear;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "semesterId")
    private Collection<Gradebook> gradebookCollection;

    public Semester() {
    }

    public Semester(Long id, String name, short academicYear) {
        this(name, academicYear);
        this.id = id;
    }

    public Semester(String name, short academicYear) {
        this.name = name;
        this.academicYear = academicYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public short getAcademicYear() {
        return academicYear;
    }

    public Collection<Gradebook> getGradebookCollection() {
        return new HashSet<>(gradebookCollection);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Semester)) {
            return false;
        }
        Semester other = (Semester) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id
                .equals(other.id))) {
            return false;
        }
        return true;
    }

}
