package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Student extends User {

    @JoinColumn(name = "group_id")
    @ManyToOne(optional = false)
    private Group groupId;

    public Student() {
    }

    public Student(Long id, String email, String firstName, String lastName, Group groupId) {
        super(id, email, firstName, lastName, GradebookConstants.ROLE_STUDENT);
        this.groupId = groupId;
    }

    public Student(String email, String firstName, String lastName, Group groupId) {
        super(email, firstName, lastName, GradebookConstants.ROLE_STUDENT);
        this.groupId = groupId;
    }

    public Group getGroupId() {
        return groupId;
    }
}
