package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;

/**
 * Created by Marcin Frankowski on 12.07.2016.
 */
@Entity
@Table(name="PRODUCT_OPERATIONS")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_OPERATION_ID"))
public class ProductOperation extends GroupObject {
    private int operationNumber;
    private String name;
    private String description;
    private int duration;
    private int preparationDuration;
    private ResourceType resourceType;
    private Product product;

    @Column(name = "OPERATION_NUMBER")
    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    @Column(name = "NAME", insertable = false, updatable = false)
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

    @Column(name = "DURATION")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Column(name = "PREPARATION_DURATION")
    public int getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(int preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    @ManyToOne
    @JoinColumn(name = "RESOURCE_TYPE_ID")
    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

