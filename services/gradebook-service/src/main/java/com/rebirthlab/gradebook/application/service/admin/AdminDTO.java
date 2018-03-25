package com.rebirthlab.gradebook.application.service.admin;

import com.rebirthlab.gradebook.application.service.user.AbstractUserDTO;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;

/**
 * Created by Anastasiy
 */
public class AdminDTO extends AbstractUserDTO {

    public AdminDTO() {
        super(GradebookConstants.ROLE_ADMIN);
    }
}
