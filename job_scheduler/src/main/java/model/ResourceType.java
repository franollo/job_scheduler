package main.java.model;

import main.java.model.common.GroupObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RESOURCE_TYPES")
@AttributeOverride(name = "id", column = @Column(name = "RESOURCE_TYPE_ID"))
public class ResourceType extends GroupObject {
    private String name;
    private String description;

    private Set<Resource> resources;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinColumn(name = "RESOURCE_TYPE_ID", referencedColumnName = "RESOURCE_TYPE_ID", updatable = false)
    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
}
