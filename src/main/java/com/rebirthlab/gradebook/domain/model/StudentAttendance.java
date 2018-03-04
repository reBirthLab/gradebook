/*
 * Copyright (C) 2015 Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rebirthlab.gradebook.domain.model;

import com.rebirthlab.gradebook.domain.model.user.Student;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "student_attendance")

@NamedQueries({
    @NamedQuery(name = "StudentAttendance.findAll", query = "SELECT s FROM StudentAttendance s"),
    @NamedQuery(name = "StudentAttendance.findByAttendanceId", query = "SELECT s FROM StudentAttendance s WHERE s.attendanceId = :attendanceId"),
    @NamedQuery(name = "StudentAttendance.findByClassDate", query = "SELECT s FROM StudentAttendance s WHERE s.classDate = :classDate"),
    @NamedQuery(name = "StudentAttendance.findByPresent", query = "SELECT s FROM StudentAttendance s WHERE s.present = :present"),
    @NamedQuery(name = "StudentAttendance.findByAbsent", query = "SELECT s FROM StudentAttendance s WHERE s.absent = :absent"),
    @NamedQuery(name = "StudentAttendance.findByAbsentWithReason", query = "SELECT s FROM StudentAttendance s WHERE s.absentWithReason = :absentWithReason")})
public class StudentAttendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "attendance_id")
    private Integer attendanceId;

    @NotNull
    @Column(name = "class_date")
    @Temporal(TemporalType.DATE)
    private Date classDate;

    @NotNull
    @Column(name = "present")
    private boolean present;

    @NotNull
    @Column(name = "absent")
    private boolean absent;

    @NotNull
    @Column(name = "absent_with_reason")
    private boolean absentWithReason;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false)
    private Student studentId;
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    @ManyToOne(optional = false)
    private Task taskId;

    public StudentAttendance() {
    }

    public StudentAttendance(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public StudentAttendance(Integer attendanceId, Date classDate, boolean present, boolean absent, boolean absentWithReason) {
        this.attendanceId = attendanceId;
        this.classDate = classDate;
        this.present = present;
        this.absent = absent;
        this.absentWithReason = absentWithReason;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

    public boolean getPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean getAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public boolean getAbsentWithReason() {
        return absentWithReason;
    }

    public void setAbsentWithReason(boolean absentWithReason) {
        this.absentWithReason = absentWithReason;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public Task getTaskId() {
        return taskId;
    }

    public void setTaskId(Task taskId) {
        this.taskId = taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceId != null ? attendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAttendance)) {
            return false;
        }
        StudentAttendance other = (StudentAttendance) object;
        if ((this.attendanceId == null && other.attendanceId != null) || (this.attendanceId != null && !this.attendanceId.equals(other.attendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entity.StudentAttendance[ attendanceId=" + attendanceId + " ]";
    }
    
}
