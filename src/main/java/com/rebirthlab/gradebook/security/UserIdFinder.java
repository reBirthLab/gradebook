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
import com.rebirthlab.gradebook.entity.Users;
import com.rebirthlab.gradebook.entity.Users_;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
public class UserIdFinder {

    EntityManager em;

    public UserIdFinder(EntityManager em) {
        this.em = em;
    }

    public UserIdType find(String username) {
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
            return new UserIdType(user.getAdminId(), GradebookConstants.ROLE_ADMIN);
        } else if (user.getLecturerId() != null) {
            return new UserIdType(user.getLecturerId(), GradebookConstants.ROLE_LECTURER);
        } else {
            return new UserIdType(user.getStudentId(), GradebookConstants.ROLE_STUDENT);
        }
    }

}
