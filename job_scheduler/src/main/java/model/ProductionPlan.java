package main.java.model;

import main.java.model.common.GroupObject;
import main.java.utils.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    private List<Item> items;
    private Set<Order> orders;

    public ProductionPlan() {}

    public ProductionPlan(int id, String name, LocalDateTime start, LocalDateTime end, LocalDateTime createdOn, LocalDateTime editedOn) {
        setId(id);
        setName(name);
        setStart(start);
        setEnd(end);
        setCreatedOn(createdOn);
        setEditedOn(editedOn);
    }

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinColumn(name = "PRODUCTION_PLAN_ID", referencedColumnName = "PRODUCTION_PLAN_ID", updatable = false)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinColumn(name = "PRODUCTION_PLAN_ID", referencedColumnName = "PRODUCTION_PLAN_ID")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
