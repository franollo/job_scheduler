package main.java.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Set<ProductOperation> productOperations;
    private Set<Resource> resources;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonBackReference
    @OneToMany
    @JoinColumn(name = "RESOURCE_TYPE_ID", referencedColumnName = "RESOURCE_TYPE_ID")
    public Set<ProductOperation> getProductOperations() {
        return productOperations;
    }

    public void setProductOperations(Set<ProductOperation> productOperations) {
        this.productOperations = productOperations;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinColumn(name = "RESOURCE_TYPE_ID", referencedColumnName = "RESOURCE_TYPE_ID")
    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
}
