package com.rebirthlab.gradebook.application.service.user;

import com.rebirthlab.gradebook.domain.model.user.User;

/**
 * Created by Anastasiy
 */
public abstract class AbstractUserServiceImpl {

    protected void patchAbstractUserDTO(AbstractUserDTO userDTO, User user) {
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
            userDTO.setFirstName(user.getFirstName());
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
            userDTO.setLastName(user.getLastName());
        }
    }
}
