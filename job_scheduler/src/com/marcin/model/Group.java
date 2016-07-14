package com.marcin.model;

import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */

@Entity
@Table(name = "GROUPS")
public class Group {
    private int groupId;
    private String name;
    private LocalDateTime createdOn;
    private LocalDateTime editedOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(LocalDateTime editedOn) {
        this.editedOn = editedOn;
    }
}
