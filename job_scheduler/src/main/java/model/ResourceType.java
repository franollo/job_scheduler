package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RESOURCE_TYPES")
@AttributeOverride(name = "id", column = @Column(name = "RESOURCE_TYPE_ID"))
public class ResourceType extends GroupObject {
    private String name;
    private Set<ProductOperation> productOperations;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "resourceType")
    public Set<ProductOperation> getProductOperations() {
        return productOperations;
    }

    public void setProductOperations(Set<ProductOperation> productOperations) {
        this.productOperations = productOperations;
    }
}
