package com.rebirthlab.gradebook.domain.model.studentgrade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Embeddable
public class StudentGradePK implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public int getTaskId() {
        return taskId;
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

}
