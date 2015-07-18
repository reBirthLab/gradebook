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
package com.rebirthlab.gradebook.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
@Table(name = "gradebook")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gradebook.findAll", query = "SELECT g FROM Gradebook g"),
    @NamedQuery(name = "Gradebook.findByGradebookId", query = "SELECT g FROM Gradebook g WHERE g.gradebookId = :gradebookId"),
    @NamedQuery(name = "Gradebook.findBySubject", query = "SELECT g FROM Gradebook g WHERE g.subject = :subject"),
    @NamedQuery(name = "Gradebook.findByDescription", query = "SELECT g FROM Gradebook g WHERE g.description = :description")})
public class Gradebook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gradebook_id")
    private Integer gradebookId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "subject")
    private String subject;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @JoinTable(name = "lecturer_has_gradebook", joinColumns = {
        @JoinColumn(name = "gradebook_id", referencedColumnName = "gradebook_id")}, inverseJoinColumns = {
        @JoinColumn(name = "lecturer_id", referencedColumnName = "lecturer_id")})
    @ManyToMany
    private Collection<Lecturer> lecturerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gradebookId")
    private Collection<Task> taskCollection;
    @JoinColumn(name = "academic_group_id", referencedColumnName = "academic_group_id")
    @ManyToOne(optional = false)
    private AcademicGroup academicGroupId;
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
    @ManyToOne(optional = false)
    private Semester semesterId;

    public Gradebook() {
    }

    public Gradebook(Integer gradebookId) {
        this.gradebookId = gradebookId;
    }

    public Gradebook(Integer gradebookId, String subject) {
        this.gradebookId = gradebookId;
        this.subject = subject;
    }

    public Integer getGradebookId() {
        return gradebookId;
    }

    public void setGradebookId(Integer gradebookId) {
        this.gradebookId = gradebookId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Lecturer> getLecturerCollection() {
        return lecturerCollection;
    }

    public void setLecturerCollection(Collection<Lecturer> lecturerCollection) {
        this.lecturerCollection = lecturerCollection;
    }

    @XmlTransient
    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public AcademicGroup getAcademicGroupId() {
        return academicGroupId;
    }

    public void setAcademicGroupId(AcademicGroup academicGroupId) {
        this.academicGroupId = academicGroupId;
    }

    public Semester getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Semester semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradebookId != null ? gradebookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gradebook)) {
            return false;
        }
        Gradebook other = (Gradebook) object;
        if ((this.gradebookId == null && other.gradebookId != null) || (this.gradebookId != null && !this.gradebookId.equals(other.gradebookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entities.Gradebook[ gradebookId=" + gradebookId + " ]";
    }
    
}
