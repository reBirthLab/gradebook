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
@Table(name = "tasks_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TasksTable.findAll", query = "SELECT t FROM TasksTable t"),
    @NamedQuery(name = "TasksTable.findByAcademicGroupId", query = "SELECT t FROM TasksTable t WHERE t.academicGroupId = :academicGroupId"),
    @NamedQuery(name = "TasksTable.findBySemesterId", query = "SELECT t FROM TasksTable t WHERE t.semesterId = :semesterId"),
    @NamedQuery(name = "TasksTable.findByGradebookId", query = "SELECT t FROM TasksTable t WHERE t.gradebookId = :gradebookId"),
    @NamedQuery(name = "TasksTable.findByTaskId", query = "SELECT t FROM TasksTable t WHERE t.taskId = :taskId"),
    @NamedQuery(name = "TasksTable.findByTitle", query = "SELECT t FROM TasksTable t WHERE t.title = :title"),
    @NamedQuery(name = "TasksTable.findByStartDate", query = "SELECT t FROM TasksTable t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "TasksTable.findByTaskLength", query = "SELECT t FROM TasksTable t WHERE t.taskLength = :taskLength"),
    @NamedQuery(name = "TasksTable.findByMaxGrade", query = "SELECT t FROM TasksTable t WHERE t.maxGrade = :maxGrade"),
    @NamedQuery(name = "TasksTable.findByStudentId", query = "SELECT t FROM TasksTable t WHERE t.studentId = :studentId"),
    @NamedQuery(name = "TasksTable.findByFirstName", query = "SELECT t FROM TasksTable t WHERE t.firstName = :firstName"),
    @NamedQuery(name = "TasksTable.findByLastName", query = "SELECT t FROM TasksTable t WHERE t.lastName = :lastName"),
    @NamedQuery(name = "TasksTable.findByGrade", query = "SELECT t FROM TasksTable t WHERE t.grade = :grade")})
public class TasksTable implements Serializable {
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
    @Column(name = "max_grade")
    private short maxGrade;
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
    @Column(name = "grade")
    private short grade;

    public TasksTable() {
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

    public short getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(short maxGrade) {
        this.maxGrade = maxGrade;
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

    public short getGrade() {
        return grade;
    }

    public void setGrade(short grade) {
        this.grade = grade;
    }
    
}
