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
    private Integer operationNumber;
    private String name;
    private String description;
    private Integer duration;
    private Integer preparationDuration;
    private Integer resourceTypeId;
    private Integer productId;
    private Integer resourceId;

    @Column(name = "OPERATION_NUMBER")
    public Integer getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(Integer operationNumber) {
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
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Column(name = "PREPARATION_DURATION")
    public Integer getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(Integer preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    @Column(name = "RESOURCE_TYPE_ID")
    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    @Column(name = "PRODUCT_ID")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Transient
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}


