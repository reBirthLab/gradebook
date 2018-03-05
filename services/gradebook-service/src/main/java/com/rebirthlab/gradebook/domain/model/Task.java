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
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "task")

@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByTaskId", query = "SELECT t FROM Task t WHERE t.taskId = :taskId"),
    @NamedQuery(name = "Task.findByTitle", query = "SELECT t FROM Task t WHERE t.title = :title"),
    @NamedQuery(name = "Task.findByStartDate", query = "SELECT t FROM Task t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "Task.findByTaskLength", query = "SELECT t FROM Task t WHERE t.taskLength = :taskLength"),
    @NamedQuery(name = "Task.findByOnCourseMon", query = "SELECT t FROM Task t WHERE t.onCourseMon = :onCourseMon"),
    @NamedQuery(name = "Task.findByOnCourseTue", query = "SELECT t FROM Task t WHERE t.onCourseTue = :onCourseTue"),
    @NamedQuery(name = "Task.findByOnCourseWed", query = "SELECT t FROM Task t WHERE t.onCourseWed = :onCourseWed"),
    @NamedQuery(name = "Task.findByOnCourseThu", query = "SELECT t FROM Task t WHERE t.onCourseThu = :onCourseThu"),
    @NamedQuery(name = "Task.findByOnCourseFri", query = "SELECT t FROM Task t WHERE t.onCourseFri = :onCourseFri"),
    @NamedQuery(name = "Task.findByMaxGrade", query = "SELECT t FROM Task t WHERE t.maxGrade = :maxGrade")})
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "task_id")
    private Integer taskId;

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
    @JoinColumn(name = "gradebook_id", referencedColumnName = "gradebook_id")
    @ManyToOne(optional = false)
    private Gradebook gradebookId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private Collection<StudentAttendance> studentAttendanceCollection;

    public Task() {
    }

    public Task(Integer taskId) {
        this.taskId = taskId;
    }

    public Task(Integer taskId, String title, Date startDate, short taskLength, boolean onCourseMon, boolean onCourseTue, boolean onCourseWed, boolean onCourseThu, boolean onCourseFri, short maxGrade) {
        this.taskId = taskId;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public short getTaskLength() {
        return taskLength;
    }

    public void setTaskLength(short taskLength) {
        this.taskLength = taskLength;
    }

    public boolean getOnCourseMon() {
        return onCourseMon;
    }

    public void setOnCourseMon(boolean onCourseMon) {
        this.onCourseMon = onCourseMon;
    }

    public boolean getOnCourseTue() {
        return onCourseTue;
    }

    public void setOnCourseTue(boolean onCourseTue) {
        this.onCourseTue = onCourseTue;
    }

    public boolean getOnCourseWed() {
        return onCourseWed;
    }

    public void setOnCourseWed(boolean onCourseWed) {
        this.onCourseWed = onCourseWed;
    }

    public boolean getOnCourseThu() {
        return onCourseThu;
    }

    public void setOnCourseThu(boolean onCourseThu) {
        this.onCourseThu = onCourseThu;
    }

    public boolean getOnCourseFri() {
        return onCourseFri;
    }

    public void setOnCourseFri(boolean onCourseFri) {
        this.onCourseFri = onCourseFri;
    }

    public short getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(short maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<StudentGrade> getStudentGradeCollection() {
        return studentGradeCollection;
    }

    public void setStudentGradeCollection(Collection<StudentGrade> studentGradeCollection) {
        this.studentGradeCollection = studentGradeCollection;
    }

    public Gradebook getGradebookId() {
        return gradebookId;
    }

    public void setGradebookId(Gradebook gradebookId) {
        this.gradebookId = gradebookId;
    }

    @XmlTransient
    public Collection<StudentAttendance> getStudentAttendanceCollection() {
        return studentAttendanceCollection;
    }

    public void setStudentAttendanceCollection(Collection<StudentAttendance> studentAttendanceCollection) {
        this.studentAttendanceCollection = studentAttendanceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entity.Task[ taskId=" + taskId + " ]";
    }
    
}
