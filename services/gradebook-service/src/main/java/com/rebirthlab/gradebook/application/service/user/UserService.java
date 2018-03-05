package com.rebirthlab.gradebook.application.service.user;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Anastasiy
 */
public interface UserService {

    Object registerUser(AbstractUserDTO user);

}
