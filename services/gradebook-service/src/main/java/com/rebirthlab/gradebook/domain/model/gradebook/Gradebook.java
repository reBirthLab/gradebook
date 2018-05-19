package com.rebirthlab.gradebook.domain.model.gradebook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rebirthlab.gradebook.domain.model.group.Group;
import com.rebirthlab.gradebook.domain.model.semester.Semester;
import com.rebirthlab.gradebook.domain.model.task.Task;
import com.rebirthlab.gradebook.domain.model.user.Lecturer;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
@Entity
public class Gradebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "group_id")
    @ManyToOne(optional = false)
    private Group groupId;

    @JoinColumn(name = "semester_id")
    @ManyToOne(optional = false)
    private Semester semesterId;

    @NotNull
    @Size(min = 1, max = 150)
    private String subject;

    @Lob
    @Size(max = 65535)
    private String description;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gradebookId")
    private Collection<Task> taskCollection;

    @JsonIgnore
    @JoinTable(name = "lecturer_has_gradebook", joinColumns = {
            @JoinColumn(name = "gradebook_id")}, inverseJoinColumns = {
            @JoinColumn(name = "lecturer_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Lecturer> lecturerCollection;

    public Gradebook() {
    }

    public Gradebook(Long id, Group groupId, Semester semesterId, String subject, String description) {
        this(groupId, semesterId, subject, description);
        this.id = id;
    }

    public Gradebook(Group groupId, Semester semesterId, String subject, String description) {
        this.groupId = groupId;
        this.semesterId = semesterId;
        this.subject = subject;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Group getGroupId() {
        return groupId;
    }

    public Semester getSemesterId() {
        return semesterId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Lecturer> getLecturerCollection() {
        return new HashSet<>(lecturerCollection);
    }

    public Collection<Task> getTaskCollection() {
        return new HashSet<>(taskCollection);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gradebook)) {
            return false;
        }
        Gradebook other = (Gradebook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id
                .equals(other.id))) {
            return false;
        }
        return true;
    }

}
