package com.rebirthlab.gradebook.domain.model.grade;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Embeddable
public class StudentGradePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long studentId;

    @NotNull
    private Long taskId;

    public StudentGradePK() {
        // Required for Hibernate to instantiate object
    }

    public StudentGradePK(Long studentId, Long taskId) {
        this.studentId = studentId;
        this.taskId = taskId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getTaskId() {
        return taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (long) studentId;
        hash += (long) taskId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof StudentGradePK)) {
            return false;
        }
        StudentGradePK other = (StudentGradePK) object;
        return this.studentId.equals(other.studentId) && this.taskId.equals(other.taskId);
    }

}
