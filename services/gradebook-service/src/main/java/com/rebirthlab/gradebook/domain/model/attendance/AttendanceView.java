package com.rebirthlab.gradebook.domain.model.attendance;

import java.io.Serializable;
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
public class AttendanceView implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private BigInteger id;

    // Group entity
    private long groupId;
    private long semesterId;
    private long gradebookId;

    // Task entity
    private long taskId;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    private short taskLength;

    // Student entity
    private long studentId;
    private String firstName;
    private String lastName;

    // Student Attendance
    private long attendanceId;
    private boolean present;
    private boolean absentWithReason;
    private boolean absent;
    @Temporal(TemporalType.DATE)
    private Date classDate;

    public AttendanceView() {
        // Required for Hibernate to instantiate object
    }

    public long getGroupId() {
        return groupId;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public long getGradebookId() {
        return gradebookId;
    }

    public long getTaskId() {
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

    public long getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getAttendanceId() {
        return attendanceId;
    }

    public boolean isPresent() {
        return present;
    }

    public boolean isAbsentWithReason() {
        return absentWithReason;
    }

    public boolean isAbsent() {
        return absent;
    }

    public Date getClassDate() {
        return classDate;
    }

}
