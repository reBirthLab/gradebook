package com.rebirthlab.gradebook.application.service.task;

import java.util.Date;

/**
 * Created by Anastasiy
 */
public class TaskDTO {

    private Long id;
    private Long gradebookId;
    private String title;
    private Date startDate;
    private short taskLength;
    private boolean onCourseMon;
    private boolean onCourseTue;
    private boolean onCourseWed;
    private boolean onCourseThu;
    private boolean onCourseFri;
    private short maxGrade;
    private String description;

    public TaskDTO() {
        // Required for Hibernate to instantiate object
    }

    public TaskDTO(Long id, Long gradebookId, String title, Date startDate, short taskLength, boolean onCourseMon,
                   boolean onCourseTue, boolean onCourseWed, boolean onCourseThu, boolean onCourseFri, short maxGrade,
                   String description) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGradebookId() {
        return gradebookId;
    }

    public void setGradebookId(Long gradebookId) {
        this.gradebookId = gradebookId;
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

    public boolean isOnCourseMon() {
        return onCourseMon;
    }

    public void setOnCourseMon(boolean onCourseMon) {
        this.onCourseMon = onCourseMon;
    }

    public boolean isOnCourseTue() {
        return onCourseTue;
    }

    public void setOnCourseTue(boolean onCourseTue) {
        this.onCourseTue = onCourseTue;
    }

    public boolean isOnCourseWed() {
        return onCourseWed;
    }

    public void setOnCourseWed(boolean onCourseWed) {
        this.onCourseWed = onCourseWed;
    }

    public boolean isOnCourseThu() {
        return onCourseThu;
    }

    public void setOnCourseThu(boolean onCourseThu) {
        this.onCourseThu = onCourseThu;
    }

    public boolean isOnCourseFri() {
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
}
