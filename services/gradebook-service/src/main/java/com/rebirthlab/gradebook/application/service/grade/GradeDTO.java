package com.rebirthlab.gradebook.application.service.grade;

/**
 * Created by Anastasiy
 */
public class GradeDTO {

    private short grade;

    public GradeDTO() {
        // Required for Hibernate to instantiate object
    }

    public GradeDTO(short grade) {
        this.grade = grade;
    }

    public short getGrade() {
        return grade;
    }

    public void setGrade(short grade) {
        this.grade = grade;
    }
}
