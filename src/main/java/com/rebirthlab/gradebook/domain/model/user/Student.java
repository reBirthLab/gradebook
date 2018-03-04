package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.model.group.Group;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @JoinColumn(name = "academic_group_id", referencedColumnName = "academic_group_id")
    @ManyToOne(optional = false)
    private Group groupId;

    public Student() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Group getGroupId() {
        return groupId;
    }

    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentId == null && other.studentId != null)
                || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

}
