package com.rebirthlab.gradebook.domain.model.grade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Embeddable
public class StudentGradeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long studentId;

    private Long taskId;

    public StudentGradeId() {
        // Required for Hibernate to instantiate object
    }

    public StudentGradeId(Long studentId, Long taskId) {
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
        return Objects.hash(studentId, taskId);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof StudentGrade)) {
            return false;
        }
        StudentGradeId other = (StudentGradeId) object;
        if (this.studentId == null || this.taskId == null) {
            return false;
        }
        return this.studentId.equals(other.studentId) && this.taskId.equals(other.taskId);
    }

}
