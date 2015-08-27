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
package com.rebirthlab.gradebook.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "attendance_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttendanceTable.findAll", query = "SELECT a FROM AttendanceTable a"),
    @NamedQuery(name = "AttendanceTable.findByAcademicGroupId", query = "SELECT a FROM AttendanceTable a WHERE a.academicGroupId = :academicGroupId"),
    @NamedQuery(name = "AttendanceTable.findBySemesterId", query = "SELECT a FROM AttendanceTable a WHERE a.semesterId = :semesterId"),
    @NamedQuery(name = "AttendanceTable.findByGradebookId", query = "SELECT a FROM AttendanceTable a WHERE a.gradebookId = :gradebookId"),
    @NamedQuery(name = "AttendanceTable.findByTaskId", query = "SELECT a FROM AttendanceTable a WHERE a.taskId = :taskId"),
    @NamedQuery(name = "AttendanceTable.findByTitle", query = "SELECT a FROM AttendanceTable a WHERE a.title = :title"),
    @NamedQuery(name = "AttendanceTable.findByStartDate", query = "SELECT a FROM AttendanceTable a WHERE a.startDate = :startDate"),
    @NamedQuery(name = "AttendanceTable.findByTaskLength", query = "SELECT a FROM AttendanceTable a WHERE a.taskLength = :taskLength"),
    @NamedQuery(name = "AttendanceTable.findByStudentId", query = "SELECT a FROM AttendanceTable a WHERE a.studentId = :studentId"),
    @NamedQuery(name = "AttendanceTable.findByFirstName", query = "SELECT a FROM AttendanceTable a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "AttendanceTable.findByLastName", query = "SELECT a FROM AttendanceTable a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "AttendanceTable.findByAttendanceId", query = "SELECT a FROM AttendanceTable a WHERE a.attendanceId = :attendanceId"),
    @NamedQuery(name = "AttendanceTable.findByPresent", query = "SELECT a FROM AttendanceTable a WHERE a.present = :present"),
    @NamedQuery(name = "AttendanceTable.findByAbsentWithReason", query = "SELECT a FROM AttendanceTable a WHERE a.absentWithReason = :absentWithReason"),
    @NamedQuery(name = "AttendanceTable.findByAbsent", query = "SELECT a FROM AttendanceTable a WHERE a.absent = :absent"),
    @NamedQuery(name = "AttendanceTable.findByClassDate", query = "SELECT a FROM AttendanceTable a WHERE a.classDate = :classDate")})
public class AttendanceTable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "academic_group_id")
    @Id
    private int academicGroupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "semester_id")
    @Id
    private int semesterId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gradebook_id")
    @Id
    private int gradebookId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_id")
    @Id
    private int taskId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_length")
    private short taskLength;
    @Basic(optional = false)
    @NotNull
    @Column(name = "student_id")
    @Id
    private int studentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "attendance_id")
    @Id
    private long attendanceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "present")
    private boolean present;
    @Basic(optional = false)
    @NotNull
    @Column(name = "absent_with_reason")
    private boolean absentWithReason;
    @Basic(optional = false)
    @NotNull
    @Column(name = "absent")
    private boolean absent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "class_date")
    @Temporal(TemporalType.DATE)
    private Date classDate;

    public AttendanceTable() {
    }

    public int getAcademicGroupId() {
        return academicGroupId;
    }

    public void setAcademicGroupId(int academicGroupId) {
        this.academicGroupId = academicGroupId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getGradebookId() {
        return gradebookId;
    }

    public void setGradebookId(int gradebookId) {
        this.gradebookId = gradebookId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public short getTaskLength() {
        return taskLength;
    }

    public void setTaskLength(short taskLength) {
        this.taskLength = taskLength;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }
    
    public boolean getPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean getAbsentWithReason() {
        return absentWithReason;
    }

    public void setAbsentWithReason(boolean absentWithReason) {
        this.absentWithReason = absentWithReason;
    }

    public boolean getAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

}
