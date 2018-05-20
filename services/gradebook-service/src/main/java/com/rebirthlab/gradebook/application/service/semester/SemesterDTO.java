package com.rebirthlab.gradebook.application.service.semester;

/**
 * Created by Anastasiy
 */
public class SemesterDTO {

    private Long id;
    private String name;
    private Short academicYear;

    public SemesterDTO() {
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

    public Short getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Short academicYear) {
        this.academicYear = academicYear;
    }

}
