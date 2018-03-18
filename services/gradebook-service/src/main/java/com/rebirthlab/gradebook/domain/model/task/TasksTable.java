package com.rebirthlab.gradebook.domain.model.task;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class TasksTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private int groupId;

    @Id
    @NotNull
    private int semesterId;

    @Id
    @NotNull
    private int gradebookId;

    @Id
    @NotNull
    private int taskId;

    @NotNull
    @Size(min = 1, max = 150)
    private String title;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @NotNull
    private short taskLength;

    @NotNull
    private short maxGrade;

    @Id
    @NotNull
    private int studentId;

    @NotNull
    @Size(min = 1, max = 45)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 45)
    private String lastName;

    @NotNull
    private short grade;

    public TasksTable() {
    }

    public int getGroupId() {
        return groupId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public int getGradebookId() {
        return gradebookId;
    }

    public int getTaskId() {
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

    public int getStudentId() {
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
