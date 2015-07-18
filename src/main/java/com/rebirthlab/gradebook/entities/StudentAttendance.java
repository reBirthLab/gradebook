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
package com.rebirthlab.gradebook.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "student_attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentAttendance.findAll", query = "SELECT s FROM StudentAttendance s"),
    @NamedQuery(name = "StudentAttendance.findByStudentId", query = "SELECT s FROM StudentAttendance s WHERE s.studentAttendancePK.studentId = :studentId"),
    @NamedQuery(name = "StudentAttendance.findByTaskId", query = "SELECT s FROM StudentAttendance s WHERE s.studentAttendancePK.taskId = :taskId"),
    @NamedQuery(name = "StudentAttendance.findByDate", query = "SELECT s FROM StudentAttendance s WHERE s.date = :date"),
    @NamedQuery(name = "StudentAttendance.findByPresent", query = "SELECT s FROM StudentAttendance s WHERE s.present = :present"),
    @NamedQuery(name = "StudentAttendance.findByAbsent", query = "SELECT s FROM StudentAttendance s WHERE s.absent = :absent"),
    @NamedQuery(name = "StudentAttendance.findByAbsentWithReason", query = "SELECT s FROM StudentAttendance s WHERE s.absentWithReason = :absentWithReason")})
public class StudentAttendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentAttendancePK studentAttendancePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "present")
    private boolean present;
    @Basic(optional = false)
    @NotNull
    @Column(name = "absent")
    private boolean absent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "absent_with_reason")
    private boolean absentWithReason;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Task task;

    public StudentAttendance() {
    }

    public StudentAttendance(StudentAttendancePK studentAttendancePK) {
        this.studentAttendancePK = studentAttendancePK;
    }

    public StudentAttendance(StudentAttendancePK studentAttendancePK, Date date, boolean present, boolean absent, boolean absentWithReason) {
        this.studentAttendancePK = studentAttendancePK;
        this.date = date;
        this.present = present;
        this.absent = absent;
        this.absentWithReason = absentWithReason;
    }

    public StudentAttendance(int studentId, int taskId) {
        this.studentAttendancePK = new StudentAttendancePK(studentId, taskId);
    }

    public StudentAttendancePK getStudentAttendancePK() {
        return studentAttendancePK;
    }

    public void setStudentAttendancePK(StudentAttendancePK studentAttendancePK) {
        this.studentAttendancePK = studentAttendancePK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentAttendancePK != null ? studentAttendancePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAttendance)) {
            return false;
        }
        StudentAttendance other = (StudentAttendance) object;
        if ((this.studentAttendancePK == null && other.studentAttendancePK != null) || (this.studentAttendancePK != null && !this.studentAttendancePK.equals(other.studentAttendancePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entities.StudentAttendance[ studentAttendancePK=" + studentAttendancePK + " ]";
    }
    
}
