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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Embeddable
public class StudentGradePK implements Serializable {

    @NotNull
    @Column(name = "student_id")
    private int studentId;

    @NotNull
    @Column(name = "task_id")
    private int taskId;

    public StudentGradePK() {
    }

    public StudentGradePK(int studentId, int taskId) {
        this.studentId = studentId;
        this.taskId = taskId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentId;
        hash += (int) taskId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentGradePK)) {
            return false;
        }
        StudentGradePK other = (StudentGradePK) object;
        if (this.studentId != other.studentId) {
            return false;
        }
        if (this.taskId != other.taskId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entity.StudentGradePK[ studentId=" + studentId + ", taskId=" + taskId + " ]";
    }
    
}
