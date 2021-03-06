package main.java.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RESOURCES")
@AttributeOverride(name = "id", column = @Column(name = "RESOURCE_ID"))
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Resource extends GroupObject {
    private String name;
    private String description;
    private Integer costPerHour;
    private Integer efficiency;
    //private Set<Item> items;
    //private Set<ProductOperation> productOperations;
    private Integer resourceTypeId;

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

    @Column(name = "COST_PER_HOUR")
    public Integer getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(Integer costPerHour) {
        this.costPerHour = costPerHour;
    }

    @Column(name = "EFFICIENCY")
    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    @Column(name = "RESOURCE_TYPE_ID", updatable = false)
    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }
}
