package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import javax.persistence.Entity;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Admin extends User {

    public Admin() {
        super(GradebookConstants.ROLE_ADMIN);
    }
}
