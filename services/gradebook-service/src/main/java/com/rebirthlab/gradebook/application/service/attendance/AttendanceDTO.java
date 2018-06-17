package com.rebirthlab.gradebook.application.service.attendance;

import java.util.Date;

/**
 * Created by Anastasiy
 */
public class AttendanceDTO {

    private Long id;

    private Long taskId;

    private Long studentId;

    private Date classDate;

    private Boolean present;

    private Boolean absent;

    private Boolean absentWithReason;

    public AttendanceDTO() {
        // Needed for Hibernate to instantiate object
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public Boolean getAbsent() {
        return absent;
    }

    public void setAbsent(Boolean absent) {
        this.absent = absent;
    }

    public Boolean getAbsentWithReason() {
        return absentWithReason;
    }

    public void setAbsentWithReason(Boolean absentWithReason) {
        this.absentWithReason = absentWithReason;
    }
}
