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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByAdminId", query = "SELECT u FROM Users u WHERE u.adminId = :adminId"),
    @NamedQuery(name = "Users.findByAdminEmail", query = "SELECT u FROM Users u WHERE u.adminEmail = :adminEmail"),
    @NamedQuery(name = "Users.findByLecturerId", query = "SELECT u FROM Users u WHERE u.lecturerId = :lecturerId"),
    @NamedQuery(name = "Users.findByLecturerEmail", query = "SELECT u FROM Users u WHERE u.lecturerEmail = :lecturerEmail"),
    @NamedQuery(name = "Users.findByStudentId", query = "SELECT u FROM Users u WHERE u.studentId = :studentId"),
    @NamedQuery(name = "Users.findByStudentEmail", query = "SELECT u FROM Users u WHERE u.studentEmail = :studentEmail")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "admin_id")
    @Id
    private Integer adminId;
    @Size(max = 45)
    @Column(name = "admin_email")
    private String adminEmail;
    @Column(name = "lecturer_id")
    @Id
    private Integer lecturerId;
    @Size(max = 45)
    @Column(name = "lecturer_email")
    private String lecturerEmail;
    @Column(name = "student_id")
    @Id
    private Integer studentId;
    @Size(max = 45)
    @Column(name = "student_email")
    private String studentEmail;

    public Users() {
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
}
