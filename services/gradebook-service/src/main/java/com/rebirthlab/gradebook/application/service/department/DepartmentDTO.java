package com.rebirthlab.gradebook.application.service.department;

/**
 * Created by Anastasiy
 */
public class DepartmentDTO {

    private Long id;
    private String name;
    private Long facultyId;

    public DepartmentDTO() {
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

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
}
