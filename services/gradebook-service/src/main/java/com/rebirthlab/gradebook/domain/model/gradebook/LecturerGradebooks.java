package com.rebirthlab.gradebook.domain.model.gradebook;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class LecturerGradebooks implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private BigInteger id;

    // Lecturer entity
    private long lecturerId;
    private String firstName;
    private String lastName;

    // Group entity
    private long groupId;
    private String number;

    // Semester entity
    private long semesterId;
    private short academicYear;
    private String name;

    // Gradebook entity
    private long gradebookId;
    private String subject;

    public LecturerGradebooks() {
    }

    public long getLecturerId() {
        return lecturerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getGroupId() {
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
