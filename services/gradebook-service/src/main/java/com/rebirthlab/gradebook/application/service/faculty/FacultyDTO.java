package com.rebirthlab.gradebook.application.service.faculty;

/**
 * Created by Anastasiy
 */
public class FacultyDTO {

    private Long id;
    private String name;

    public FacultyDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
