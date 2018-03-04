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
    private Integer groupId;

    @NotNull
    @Column(name = "number")
    private String number;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<Student> studentCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId")
    private Collection<Gradebook> gradebookCollection;

    @JoinColumn(name = "faculty_id", referencedColumnName = "faculty_id")
    @ManyToOne(optional = false)
    private Faculty facultyId;

    public Group() {
    }

    public Group(Integer academicGroupId) {
        this.groupId = academicGroupId;
    }

    public Group(Integer academicGroupId, String number) {
        this.groupId = academicGroupId;
        this.number = number;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    public Collection<Gradebook> getGradebookCollection() {
        return gradebookCollection;
    }

    public void setGradebookCollection(Collection<Gradebook> gradebookCollection) {
        this.gradebookCollection = gradebookCollection;
    }

    public Faculty getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Faculty facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Group)) {
            return false;
        }
        Group other = (Group) object;
        if ((this.groupId == null && other.groupId != null)
                || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

}
