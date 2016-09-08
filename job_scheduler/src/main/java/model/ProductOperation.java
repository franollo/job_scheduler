package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Table(name="PRODUCT_OPERATIONS")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_OPERATION_ID"))
public class ProductOperation extends GroupObject {
    private Integer operationNumber;
    private String name;
    private String description;
    private Duration duration;
    private Duration preparationDuration;
    private boolean sequential;
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
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Column(name = "PREPARATION_DURATION")
    public Duration getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(Duration preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    @Column(name = "SEQUENTIAL")
    public boolean isSequential() {
        return sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }

    @Column(name="RESOURCE_ID")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}


