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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "lecturer_gradebooks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LecturerGradebooks.findAll", query = "SELECT l FROM LecturerGradebooks l"),
    @NamedQuery(name = "LecturerGradebooks.findByLecturerId", query = "SELECT l FROM LecturerGradebooks l WHERE l.lecturerId = :lecturerId"),
    @NamedQuery(name = "LecturerGradebooks.findByFirstName", query = "SELECT l FROM LecturerGradebooks l WHERE l.firstName = :firstName"),
    @NamedQuery(name = "LecturerGradebooks.findByLastName", query = "SELECT l FROM LecturerGradebooks l WHERE l.lastName = :lastName"),
    @NamedQuery(name = "LecturerGradebooks.findByAcademicGroupId", query = "SELECT l FROM LecturerGradebooks l WHERE l.academicGroupId = :academicGroupId"),
    @NamedQuery(name = "LecturerGradebooks.findByNumber", query = "SELECT l FROM LecturerGradebooks l WHERE l.number = :number"),
    @NamedQuery(name = "LecturerGradebooks.findBySemesterId", query = "SELECT l FROM LecturerGradebooks l WHERE l.semesterId = :semesterId"),
    @NamedQuery(name = "LecturerGradebooks.findByAcademicYear", query = "SELECT l FROM LecturerGradebooks l WHERE l.academicYear = :academicYear"),
    @NamedQuery(name = "LecturerGradebooks.findByName", query = "SELECT l FROM LecturerGradebooks l WHERE l.name = :name"),
    @NamedQuery(name = "LecturerGradebooks.findByGradebookId", query = "SELECT l FROM LecturerGradebooks l WHERE l.gradebookId = :gradebookId"),
    @NamedQuery(name = "LecturerGradebooks.findBySubject", query = "SELECT l FROM LecturerGradebooks l WHERE l.subject = :subject")})
public class LecturerGradebooks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lecturer_id")
    private int lecturerId;
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
    private short academicYear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gradebook_id")
    @Id
    private int gradebookId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "subject")
    private String subject;

    public LecturerGradebooks() {
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
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

    public short getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(short academicYear) {
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
