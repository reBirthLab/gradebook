package com.rebirthlab.gradebook.application.service.faculty;

/**
 * Created by Anastasiy
 */
public class FacultyDTO {

    private Long id;
    private String name;

    public FacultyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FacultyDTO(String name) {
        this.name = name;
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
