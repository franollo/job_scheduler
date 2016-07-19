package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RESOURCE_TYPES")
public class ResourceType extends GroupObject {
  //  private int resourceTypeId;
    private String name;
    private Set<ProductOperation> productOperations;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "RESOURCE_TYPE_ID")
//    public int getResourceTypeId() {
//        return resourceTypeId;
//    }
//
//    public void setResourceTypeId(int resourceTypeId) {
//        this.resourceTypeId = resourceTypeId;
//    }

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
