package com.rebirthlab.gradebook.domain.model.gradebook;

import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class StudentGradebooks {

    @Id
    private BigInteger id;

    // Student entity
    private long studentId;
    private String firstName;
    private String lastName;

    // Group entity
    private int groupId;
    private String number;

    // Semester entity
    private long semesterId;
    private short academicYear;
    private String name;

    // Gradebook entity
    private long gradebookId;
    private String subject;

    public StudentGradebooks() {
    }

    public long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getNumber() {
        return number;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public short getAcademicYear() {
        return academicYear;
    }

    public String getName() {
        return name;
    }

    public long getGradebookId() {
        return gradebookId;
    }

    public String getSubject() {
        return subject;
    }
}
