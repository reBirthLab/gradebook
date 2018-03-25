package com.rebirthlab.gradebook.application.service.lecturer;

import com.rebirthlab.gradebook.application.service.user.AbstractUserDTO;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;

/**
 * Created by Anastasiy
 */
public class LecturerDTO extends AbstractUserDTO {

    private Long departmentId;

    public LecturerDTO() {
        super(GradebookConstants.ROLE_LECTURER);
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

}
