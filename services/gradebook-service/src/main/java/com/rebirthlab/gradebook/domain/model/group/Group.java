package com.rebirthlab.gradebook.domain.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.faculty.Faculty;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.user.Student;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "\"group\"")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String number;

    @JoinColumn(name = "faculty_id")
    @ManyToOne(optional = false)
    private Faculty facultyId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<Student> studentCollection;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<Gradebook> gradebookCollection;

    public Group() {
    }

    public Group(Long id, String number, Faculty facultyId) {
        this.id = id;
        this.number = number;
        this.facultyId = facultyId;
    }

    public Group(String number, Faculty facultyId) {
        this.number = number;
        this.facultyId = facultyId;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Faculty getFacultyId() {
        return facultyId;
    }

    public Collection<Student> getStudentCollection() {
        return new HashSet<>(studentCollection);
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
        if (!(object instanceof Group)) {
            return false;
        }
        Group other = (Group) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
