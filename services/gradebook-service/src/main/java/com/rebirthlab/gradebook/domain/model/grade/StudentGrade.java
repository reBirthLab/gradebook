package com.rebirthlab.gradebook.domain.model.grade;

import com.rebirthlab.gradebook.domain.model.task.Task;
import com.rebirthlab.gradebook.domain.model.user.Student;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class StudentGrade {

    @EmbeddedId
    private StudentGradePK studentGradePK;

    @NotNull
    @Column(name = "grade")
    private short grade;

    @MapsId("studentId")
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Student student;

    @MapsId("taskId")
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Task task;

    public StudentGrade() {
        // Required for Hibernate to instantiate object
    }

    public StudentGrade(Student student, Task task, short grade) {
        this.studentGradePK = new StudentGradePK(student.getId(), task.getId());
        this.grade = grade;
        this.student = student;
        this.task = task;
    }

    public StudentGradePK getStudentGradePK() {
        return studentGradePK;
    }

    public short getGrade() {
        return grade;
    }

    public Student getStudent() {
        return student;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentGradePK != null ? studentGradePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof StudentGrade)) {
            return false;
        }
        StudentGrade other = (StudentGrade) object;
        return this.studentGradePK != null && other.studentGradePK != null
                && this.studentGradePK.equals(other.studentGradePK);
    }

}
