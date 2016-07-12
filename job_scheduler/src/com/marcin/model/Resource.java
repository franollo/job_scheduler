package com.marcin.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RESOURCES")
public class Resource {
    private int resourceId;
    private String name;
    private String description;
    private int costPerHour;
    private int efficiency;
    private boolean usagePlot;
    private Set<ProductOperation> productOperations;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESOURCE_ID")
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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

    @Column(name = "COST_PER_HOUR")
    public int getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(int costPerHour) {
        this.costPerHour = costPerHour;
    }

    @Column(name = "EFFICIENCY")
    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    @Column(name = "USAGE_PLOT")
    public boolean getUsagePlot() {
        return usagePlot;
    }

    public void setUsagePlot(boolean usagePlot) {
        this.usagePlot = usagePlot;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "resource")
    public Set<ProductOperation> getProductOperations() {
        return productOperations;
    }

    public void setProductOperations(Set<ProductOperation> productOperations) {
        this.productOperations = productOperations;
    }
}
