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
package com.rebirthlab.gradebook.service;

import com.rebirthlab.gradebook.common.GradebookConstants;
import com.rebirthlab.gradebook.entity.Gradebook;
import com.rebirthlab.gradebook.entity.LecturerGradebooks;
import com.rebirthlab.gradebook.entity.LecturerGradebooks_;
import com.rebirthlab.gradebook.security.AuthenticationService;
import com.rebirthlab.gradebook.security.CurrentUser;
import com.rebirthlab.gradebook.security.UserDataFinder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Stateless
@Path("gradebooks")
public class LecturerGradebooksFacadeREST extends AbstractFacade<LecturerGradebooks> {

    @PersistenceContext(unitName = "com.rebirthlab_gradebook_war_1.0PU")
    private EntityManager em;

    public LecturerGradebooksFacadeREST() {
        super(LecturerGradebooks.class);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<LecturerGradebooks> findAllLecturerGradebooks(@HeaderParam("Authorization") String authorization){

        String username = new AuthenticationService().getUsername(authorization);

        CurrentUser user = UserDataFinder.findDataBy(username);

        if (user.getRole().equals(GradebookConstants.ROLE_LECTURER)) {
            Integer lecturerId = user.getId();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(LecturerGradebooks.class);
            Root lecturerGradebooks = cq.from(LecturerGradebooks.class);
            
            cq.where(cb.equal(lecturerGradebooks.get(LecturerGradebooks_.lecturerId), lecturerId));
            return getEntityManager().createQuery(cq).getResultList();
        } else {
            return null;
        }
    }    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
