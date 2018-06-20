package com.rebirthlab.gradebook.domain.model.grade;

import com.rebirthlab.gradebook.domain.model.task.Task;
import com.rebirthlab.gradebook.domain.model.user.Student;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@IdClass(StudentGradeId.class)
public class StudentGrade {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

    @Id
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskId;

    @NotNull
    private short grade;

    public StudentGrade() {
        // Required for Hibernate to instantiate object
    }

    public StudentGrade(Student studentId, Task taskId, short grade) {
        this.studentId = studentId;
        this.taskId = taskId;
        this.grade = grade;
    }

    public Student getStudentId() {
        return studentId;
    }

    public Task getTaskId() {
        return taskId;
    }

    public short getGrade() {
        return grade;
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
        StudentGrade other = (StudentGrade) object;
        if (this.studentId == null || this.taskId == null) {
            return false;
        }
        return this.studentId.equals(other.studentId) && this.taskId.equals(other.taskId);
    }

}
