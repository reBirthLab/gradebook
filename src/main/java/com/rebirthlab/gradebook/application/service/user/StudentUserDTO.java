package com.rebirthlab.gradebook.application.service.user;

/**
 * Created by Anastasiy
 */
public class StudentUserDTO extends AbstractUserDTO {

    private String firstName;
    private String lastName;
    private String groupNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

}
