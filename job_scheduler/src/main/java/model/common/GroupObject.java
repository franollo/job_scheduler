package main.java.model.common;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 18.07.2016.
 */

@MappedSuperclass
public abstract class GroupObject {
    private Integer id;
    private Integer groupId;
    private LocalDateTime createdOn;
    private LocalDateTime editedOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "GROUP_ID",  updatable = false)
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Column(name = "CREATED_ON", updatable = false)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "EDITED_ON")
    public LocalDateTime getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(LocalDateTime editedOn) {
        this.editedOn = editedOn;
    }

    @PrePersist
    public void setDates() {
        createdOn = LocalDateTime.now();
        editedOn = LocalDateTime.now();
    }

    @PreUpdate
    public void setEditedOn() {
        editedOn = LocalDateTime.now();
    }
}
