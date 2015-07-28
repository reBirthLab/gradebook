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
@Table(name = "student_gradebooks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentGradebooks.findAll", query = "SELECT s FROM StudentGradebooks s"),
    @NamedQuery(name = "StudentGradebooks.findByStudentId", query = "SELECT s FROM StudentGradebooks s WHERE s.studentId = :studentId"),
    @NamedQuery(name = "StudentGradebooks.findByFirstName", query = "SELECT s FROM StudentGradebooks s WHERE s.firstName = :firstName"),
    @NamedQuery(name = "StudentGradebooks.findByLastName", query = "SELECT s FROM StudentGradebooks s WHERE s.lastName = :lastName"),
    @NamedQuery(name = "StudentGradebooks.findByAcademicGroupId", query = "SELECT s FROM StudentGradebooks s WHERE s.academicGroupId = :academicGroupId"),
    @NamedQuery(name = "StudentGradebooks.findByNumber", query = "SELECT s FROM StudentGradebooks s WHERE s.number = :number"),
    @NamedQuery(name = "StudentGradebooks.findBySemesterId", query = "SELECT s FROM StudentGradebooks s WHERE s.semesterId = :semesterId"),
    @NamedQuery(name = "StudentGradebooks.findByAcademicYear", query = "SELECT s FROM StudentGradebooks s WHERE s.academicYear = :academicYear"),
    @NamedQuery(name = "StudentGradebooks.findByName", query = "SELECT s FROM StudentGradebooks s WHERE s.name = :name"),
    @NamedQuery(name = "StudentGradebooks.findByGradebookId", query = "SELECT s FROM StudentGradebooks s WHERE s.gradebookId = :gradebookId"),
    @NamedQuery(name = "StudentGradebooks.findBySubject", query = "SELECT s FROM StudentGradebooks s WHERE s.subject = :subject")})
public class StudentGradebooks implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Column(name = "academic_group_id")
    private int academicGroupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "number")
    private String number;
    @Basic(optional = false)
    @NotNull
    @Column(name = "semester_id")
    private int semesterId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "academic_year")
    @Temporal(TemporalType.DATE)
    private Date academicYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gradebook_id")
    private int gradebookId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "subject")
    private String subject;

    public StudentGradebooks() {
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

    public int getAcademicGroupId() {
        return academicGroupId;
    }

    public void setAcademicGroupId(int academicGroupId) {
        this.academicGroupId = academicGroupId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public Date getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Date academicYear) {
        this.academicYear = academicYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGradebookId() {
        return gradebookId;
    }

    public void setGradebookId(int gradebookId) {
        this.gradebookId = gradebookId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}
