/*
 * Copyright (C) 2015 Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rebirthlab.gradebook.security;

import com.rebirthlab.gradebook.common.GradebookConstants;
import com.rebirthlab.gradebook.entity.Admin;
import com.rebirthlab.gradebook.entity.Lecturer;
import com.rebirthlab.gradebook.entity.Student;
import com.rebirthlab.gradebook.entity.Users;
import com.rebirthlab.gradebook.entity.Users_;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
public class UserDataFinder {

    private static EntityManager em;
    private static CurrentUser currentUser;

    public static CurrentUser findDataBy(String username) {
        currentUser = new CurrentUser();
        currentUser.setUsername(username);
        findUserIdAndRole(username);
        findUserPassword();
        return currentUser;
    }

    private static void findUserIdAndRole(String username) {
        try {
            em = (EntityManager) new InitialContext().lookup("java:comp/env/PersistenceContext");
        } catch (NamingException ex) {
            Logger.getLogger(UserDataFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Users.class);

        Root users = cq.from(Users.class);
        cq.where(
                cb.or(
                        cb.equal(users.get(Users_.adminEmail), username),
                        cb.equal(users.get(Users_.lecturerEmail), username),
                        cb.equal(users.get(Users_.studentEmail), username)
                ));

        Users user = (Users) em.createQuery(cq).getSingleResult();

        if (user.getAdminId() != null) {
            currentUser.setId(user.getAdminId());
            currentUser.setRole(GradebookConstants.ROLE_ADMIN);
        } else if (user.getLecturerId() != null) {
            currentUser.setId(user.getLecturerId());
            currentUser.setRole(GradebookConstants.ROLE_LECTURER);
        } else {
            currentUser.setId(user.getStudentId());
            currentUser.setRole(GradebookConstants.ROLE_STUDENT);
        }
    }

    private static void findUserPassword() {
        try {
            em = (EntityManager) new InitialContext().lookup("java:comp/env/UsersPersistenceContext");
        } catch (NamingException ex) {
            Logger.getLogger(UserDataFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (currentUser.getRole()) {
            case GradebookConstants.ROLE_ADMIN:
                Admin admin = em.find(Admin.class, currentUser.getId());
                currentUser.setPassword(admin.getPassword());
                break;
            case GradebookConstants.ROLE_LECTURER:
                Lecturer lecturer = em.find(Lecturer.class, currentUser.getId());
                currentUser.setPassword(lecturer.getPassword());
                break;
            case GradebookConstants.ROLE_STUDENT:
                Student student = em.find(Student.class, currentUser.getId());
                currentUser.setPassword(student.getPassword());
                break;
        }

    }

}
