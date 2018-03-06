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
package com.rebirthlab.gradebook.application.security;

import com.rebirthlab.gradebook.domain.model.user.Admin;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import com.rebirthlab.gradebook.domain.model.user.Student;
import com.rebirthlab.gradebook.domain.shared.GradebookConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
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

            CriteriaBuilder cb = em.getCriteriaBuilder();
           // CriteriaQuery cq = cb.createQuery(User.class);

     /*       Root users = cq.from(AbstractUserDTO.class);
            cq.where(
                    cb.or(
                            cb.equal(users.get(Users_.adminEmail), username),
                            cb.equal(users.get(Users_.lecturerEmail), username),
                            cb.equal(users.get(Users_.studentEmail), username)
                    ));

            AbstractUserDTO user = (AbstractUserDTO) em.createQuery(cq).getSingleResult();

            if (user.getAdminId() != null) {
                currentUser.setId(user.getAdminId());
                currentUser.setRole(GradebookConstants.ROLE_ADMIN);
            } else if (user.getLecturerId() != null) {
                currentUser.setId(user.getLecturerId());
                currentUser.setRole(GradebookConstants.ROLE_LECTURER);
            } else {
                currentUser.setId(user.getStudentId());
                currentUser.setRole(GradebookConstants.ROLE_STUDENT);
            }*/
        } catch (NamingException ex) {
            Logger.getLogger(UserDataFinder.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (NoResultException e) {
            Logger.getLogger(UserDataFinder.class.getName())
                    .log(Level.SEVERE, "No such user data in system", e);
        }
    }

    private static void findUserPassword() {
        try {
            em = (EntityManager) new InitialContext().lookup("java:comp/env/PersistenceContext");

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
                    //currentUser.setPassword(student.getPassword());
                    break;
            }
        } catch (NamingException ex) {
            Logger.getLogger(UserDataFinder.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (NullPointerException e) {
            Logger.getLogger(UserDataFinder.class.getName())
                    .log(Level.SEVERE, "No such user data in system", e);
        }

    }

}
