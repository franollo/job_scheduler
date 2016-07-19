package main.java.model.common;

import main.java.model.Group;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 18.07.2016.
 */

@MappedSuperclass
public abstract class GroupObject {
    private int id;
    private Group group;
    private LocalDateTime createdOn;
    private LocalDateTime editedOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(name = "CREATED_ON")
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
}
