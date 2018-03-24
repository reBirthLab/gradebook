package com.rebirthlab.gradebook.application.service.student;

import com.rebirthlab.gradebook.application.service.user.AbstractUserDTO;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;

/**
 * Created by Anastasiy
 */
public class StudentDTO extends AbstractUserDTO {

    private Long groupId;

    public StudentDTO() {
        super(GradebookConstants.ROLE_STUDENT);
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
