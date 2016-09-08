package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;


@Entity
@Table(name = "USERS")
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
public class User extends GroupObject {
    private String username;
    private String firstName;
    private String lastName;

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
