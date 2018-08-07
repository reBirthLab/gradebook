package com.rebirthlab.gradebook.domain.model.task;

import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class TaskView {

    @Id
    private BigInteger id;

    // Group entity
    private Long groupId;
    private Long semesterId;
    private Long gradebookId;

    // Task entity
    private Long taskId;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    private short taskLength;
    private short maxGrade;

    // Student entity
    private Long studentId;
    private String firstName;
    private String lastName;

    //Student Grade
    private short grade;

    public TaskView() {
        // Required for Hibernate to instantiate object
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public Long getGradebookId() {
        return gradebookId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public short getTaskLength() {
        return taskLength;
    }

    public short getMaxGrade() {
        return maxGrade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public short getGrade() {
        return grade;
    }
}
