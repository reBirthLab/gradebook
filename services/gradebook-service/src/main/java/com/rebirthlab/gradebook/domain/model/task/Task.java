package com.rebirthlab.gradebook.domain.model.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.attendance.StudentAttendance;
import com.rebirthlab.gradebook.domain.model.grade.StudentGrade;
import com.rebirthlab.gradebook.domain.model.gradebook.Gradebook;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    private String title;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @NotNull
    private short taskLength;

    @NotNull
    private boolean onCourseMon;

    @NotNull
    private boolean onCourseTue;

    @NotNull
    private boolean onCourseWed;

    @NotNull
    private boolean onCourseThu;

    @NotNull
    private boolean onCourseFri;

    @NotNull
    private short maxGrade;

    @Lob
    @Size(max = 65535)
    private String description;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private Collection<StudentGrade> studentGradeCollection;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private Collection<StudentAttendance> studentAttendanceCollection;

    public Task() {
    }

    public Task(Gradebook gradebookId, String title, Date startDate, short taskLength, boolean onCourseMon,
                boolean onCourseTue, boolean onCourseWed, boolean onCourseThu, boolean onCourseFri, short maxGrade,
                String description) {
        this.gradebookId = gradebookId;
        this.title = title;
        this.startDate = startDate;
        this.taskLength = taskLength;
        this.onCourseMon = onCourseMon;
        this.onCourseTue = onCourseTue;
        this.onCourseWed = onCourseWed;
        this.onCourseThu = onCourseThu;
        this.onCourseFri = onCourseFri;
        this.maxGrade = maxGrade;
        this.description = description;
    }

    public Task(Long id, Gradebook gradebookId, String title, Date startDate, short taskLength, boolean onCourseMon,
                boolean onCourseTue, boolean onCourseWed, boolean onCourseThu, boolean onCourseFri, short maxGrade,
                String description) {
        this(gradebookId, title, startDate, taskLength, onCourseMon, onCourseTue, onCourseWed, onCourseThu, onCourseFri,
                maxGrade, description);
        this.id = id;
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
        if (object == null) return false;
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        return (this.id != null && other.id != null) && this.id.equals(other.id);
    }

}
