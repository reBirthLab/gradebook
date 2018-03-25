package com.rebirthlab.gradebook.domain.model.user;

import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import javax.persistence.Entity;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Admin extends User {

    public Admin() {
    }

    public Admin(Long id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName, GradebookConstants.ROLE_ADMIN);
    }

    public Admin(String email, String firstName, String lastName) {
        super(email, firstName, lastName, GradebookConstants.ROLE_ADMIN);
    }
}
