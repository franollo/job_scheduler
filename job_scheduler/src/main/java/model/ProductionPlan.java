package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Marcin Frankowski on 12.07.2016.
 */
@Entity
@Table(name = "PRODUCTION_PLANS")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCTION_PLAN_ID"))
public class ProductionPlan extends GroupObject {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Set<Item> items;
    private Set<Order> orders;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "START")
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    @Column(name = "END")
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productionPlan")
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productionPlan")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void process() {

    }
}
