package com.rebirthlab.gradebook.application.service.student;

import com.rebirthlab.gradebook.application.service.user.AbstractUserDTO;

/**
 * Created by Anastasiy
 */
public class StudentDTO extends AbstractUserDTO {

    private Long groupId;

    public StudentDTO() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
