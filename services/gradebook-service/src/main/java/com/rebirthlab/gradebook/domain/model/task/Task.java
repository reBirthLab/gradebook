package com.rebirthlab.gradebook.domain.model.task;

import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendance;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import com.rebirthlab.gradebook.domain.model.studentgrade.StudentGrade;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "gradebook_id")
    @ManyToOne(optional = false)
    private Gradebook gradebookId;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @NotNull
    @Column(name = "task_length")
    private short taskLength;

    @NotNull
    @Column(name = "on_course_mon")
    private boolean onCourseMon;

    @NotNull
    @Column(name = "on_course_tue")
    private boolean onCourseTue;

    @NotNull
    @Column(name = "on_course_wed")
    private boolean onCourseWed;

    @NotNull
    @Column(name = "on_course_thu")
    private boolean onCourseThu;

    @NotNull
    @Column(name = "on_course_fri")
    private boolean onCourseFri;

    @NotNull
    @Column(name = "max_grade")
    private short maxGrade;

    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Collection<StudentGrade> studentGradeCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private Collection<StudentAttendance> studentAttendanceCollection;

    public Task() {
    }

    public Task(String title, Date startDate, short taskLength, boolean onCourseMon,
                boolean onCourseTue, boolean onCourseWed, boolean onCourseThu, boolean onCourseFri, short maxGrade) {
        this.title = title;
        this.startDate = startDate;
        this.taskLength = taskLength;
        this.onCourseMon = onCourseMon;
        this.onCourseTue = onCourseTue;
        this.onCourseWed = onCourseWed;
        this.onCourseThu = onCourseThu;
        this.onCourseFri = onCourseFri;
        this.maxGrade = maxGrade;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public short getTaskLength() {
        return taskLength;
    }

    public boolean getOnCourseMon() {
        return onCourseMon;
    }

    public boolean getOnCourseTue() {
        return onCourseTue;
    }

    public boolean getOnCourseWed() {
        return onCourseWed;
    }

    public boolean getOnCourseThu() {
        return onCourseThu;
    }

    public boolean getOnCourseFri() {
        return onCourseFri;
    }

    public short getMaxGrade() {
        return maxGrade;
    }

    public String getDescription() {
        return description;
    }

    public Collection<StudentGrade> getStudentGradeCollection() {
        return studentGradeCollection;
    }

    public Gradebook getGradebookId() {
        return gradebookId;
    }

    public Collection<StudentAttendance> getStudentAttendanceCollection() {
        return studentAttendanceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id
                .equals(other.id))) {
            return false;
        }
        return true;
    }

}
