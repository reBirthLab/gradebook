package com.rebirthlab.gradebook.domain.model.attendance;

import com.rebirthlab.gradebook.domain.model.task.Task;
import com.rebirthlab.gradebook.domain.model.user.Student;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class StudentAttendance implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "task_id")
    @ManyToOne(optional = false)
    private Task taskId;

    @JoinColumn(name = "student_id")
    @ManyToOne(optional = false)
    private Student studentId;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date classDate;

    private boolean present;
    private boolean absent;
    private boolean absentWithReason;

    public StudentAttendance() {
    }

    public StudentAttendance(Task taskId, Student studentId, Date classDate) {
        this.taskId = taskId;
        this.studentId = studentId;
        this.classDate = classDate;
    }

    public StudentAttendance(Task taskId, Student studentId, Date classDate,
                             boolean present, boolean absent, boolean absentWithReason) {
        this(taskId, studentId, classDate);
        this.present = present;
        this.absent = absent;
        this.absentWithReason = absentWithReason;
    }

    public StudentAttendance(Long id, Task taskId, Student studentId, Date classDate,
                             boolean present, boolean absent, boolean absentWithReason) {
        this(taskId, studentId, classDate, present, absent, absentWithReason);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getClassDate() {
        return classDate;
    }

    public boolean getPresent() {
        return present;
    }

    public boolean getAbsent() {
        return absent;
    }

    public boolean getAbsentWithReason() {
        return absentWithReason;
    }

    public Student getStudentId() {
        return studentId;
    }

    public Task getTaskId() {
        return taskId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof StudentAttendance)) {
            return false;
        }
        StudentAttendance other = (StudentAttendance) object;
        return (this.id != null && other.id != null) && this.id.equals(other.id);
    }

}
