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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "student_grade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentGrade.findAll", query = "SELECT s FROM StudentGrade s")})
public class StudentGrade implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentGradePK studentGradePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grade")
    private short grade;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Task task;

    public StudentGrade() {
    }

    public StudentGrade(StudentGradePK studentGradePK) {
        this.studentGradePK = studentGradePK;
    }

    public StudentGrade(StudentGradePK studentGradePK, short grade) {
        this.studentGradePK = studentGradePK;
        this.grade = grade;
    }

    public StudentGrade(int studentId, int taskId) {
        this.studentGradePK = new StudentGradePK(studentId, taskId);
    }

    public StudentGradePK getStudentGradePK() {
        return studentGradePK;
    }

    public void setStudentGradePK(StudentGradePK studentGradePK) {
        this.studentGradePK = studentGradePK;
    }

    public short getGrade() {
        return grade;
    }

    public void setGrade(short grade) {
        this.grade = grade;
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
        hash += (studentGradePK != null ? studentGradePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentGrade)) {
            return false;
        }
        StudentGrade other = (StudentGrade) object;
        if ((this.studentGradePK == null && other.studentGradePK != null) || (this.studentGradePK != null && !this.studentGradePK.equals(other.studentGradePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entities.StudentGrade[ studentGradePK=" + studentGradePK + " ]";
    }
    
}
