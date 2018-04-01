package com.rebirthlab.gradebook.application.service.group;

/**
 * Created by Anastasiy
 */
public class GroupDTO {

    private Long id;
    private String number;
    private Long facultyId;

    public GroupDTO() {
    }

    public GroupDTO(Long id, String number, Long facultyId) {
        this.id = id;
        this.number = number;
        this.facultyId = facultyId;
    }

    public GroupDTO(String number, Long facultyId) {
        this.number = number;
        this.facultyId = facultyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
}
