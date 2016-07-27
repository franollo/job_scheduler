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
    private int resourceTypeId;
    private int productId;
    private int resourceId;

    @Column(name = "OPERATION_NUMBER")
    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

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

    @Column(name = "RESOURCE_TYPE_ID")
    public int getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(int resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    @Column(name = "PRODUCT_ID")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Transient
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}


