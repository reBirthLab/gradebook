package com.rebirthlab.gradebook.application.service.gradebook;

import java.util.Set;

/**
 * Created by Anastasiy
 */
public class GradebookDTO {

    private Long id;
    private Long groupId;
    private Long semesterId;
    private String subject;
    private String description;
    private Set<Long> lecturerCollection;

    public GradebookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getLecturerCollection() {
        return lecturerCollection;
    }

    public void setLecturerCollection(Set<Long> lecturerCollection) {
        this.lecturerCollection = lecturerCollection;
    }
}
