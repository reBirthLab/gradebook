package com.rebirthlab.gradebook.domain.model.group;

import com.rebirthlab.gradebook.domain.model.Faculty;
import com.rebirthlab.gradebook.domain.model.Gradebook;
import com.rebirthlab.gradebook.domain.model.user.Student;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String number;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<Student> studentCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<Gradebook> gradebookCollection;

    @JoinColumn(name = "faculty_id")
    @ManyToOne(optional = false)
    private Faculty facultyId;

    public Group() {
    }

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public Collection<Gradebook> getGradebookCollection() {
        return gradebookCollection;
    }

    public Faculty getFacultyId() {
        return facultyId;
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
