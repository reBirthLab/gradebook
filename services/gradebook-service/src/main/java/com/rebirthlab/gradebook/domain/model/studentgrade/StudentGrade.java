package com.rebirthlab.gradebook.domain.model.studentgrade;

import com.rebirthlab.gradebook.domain.model.task.Task;
import com.rebirthlab.gradebook.domain.model.user.Student;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class StudentGrade {

    @EmbeddedId
    protected StudentGradePK studentGradePK;

    @NotNull
    @Column(name = "grade")
    private short grade;

    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;

    @JoinColumn(name = "task_id", insertable = false, updatable = false)
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
        if ((this.studentGradePK == null && other.studentGradePK != null) || (this.studentGradePK != null && !this.studentGradePK
                .equals(other.studentGradePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entity.StudentGrade[ studentGradePK=" + studentGradePK + " ]";
    }

}
