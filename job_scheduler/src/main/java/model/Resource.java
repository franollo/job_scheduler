package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RESOURCES")
@AttributeOverride(name = "id", column = @Column(name = "RESOURCE_ID"))
public class Resource extends GroupObject {
    private String name;
    private String description;
    private int costPerHour;
    private int efficiency;
    private Set<Item> items;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "resource")
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
