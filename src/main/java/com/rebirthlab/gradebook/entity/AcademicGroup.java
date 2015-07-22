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
package com.rebirthlab.gradebook.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "academic_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcademicGroup.findAll", query = "SELECT a FROM AcademicGroup a"),
    @NamedQuery(name = "AcademicGroup.findByAcademicGroupId", query = "SELECT a FROM AcademicGroup a WHERE a.academicGroupId = :academicGroupId"),
    @NamedQuery(name = "AcademicGroup.findByNumber", query = "SELECT a FROM AcademicGroup a WHERE a.number = :number")})
public class AcademicGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "academic_group_id")
    private Integer academicGroupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "number")
    private String number;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicGroupId")
    private Collection<Student> studentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "academicGroupId")
    private Collection<Gradebook> gradebookCollection;

    public AcademicGroup() {
    }

    public AcademicGroup(Integer academicGroupId) {
        this.academicGroupId = academicGroupId;
    }

    public AcademicGroup(Integer academicGroupId, String number) {
        this.academicGroupId = academicGroupId;
        this.number = number;
    }

    public Integer getAcademicGroupId() {
        return academicGroupId;
    }

    public void setAcademicGroupId(Integer academicGroupId) {
        this.academicGroupId = academicGroupId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @XmlTransient
    public Collection<Gradebook> getGradebookCollection() {
        return gradebookCollection;
    }

    public void setGradebookCollection(Collection<Gradebook> gradebookCollection) {
        this.gradebookCollection = gradebookCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (academicGroupId != null ? academicGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicGroup)) {
            return false;
        }
        AcademicGroup other = (AcademicGroup) object;
        if ((this.academicGroupId == null && other.academicGroupId != null) || (this.academicGroupId != null && !this.academicGroupId.equals(other.academicGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rebirthlab.gradebook.entity.AcademicGroup[ academicGroupId=" + academicGroupId + " ]";
    }
    
}
