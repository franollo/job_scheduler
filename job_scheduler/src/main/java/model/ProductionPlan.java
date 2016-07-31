package main.java.model;

import main.java.model.common.GroupObject;
import main.java.utils.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;

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

    public ProductionPlan() {}

    public ProductionPlan(int productionPlanId) {
        setId(productionPlanId);
    }

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
    @JoinColumn(name = "PRODUCTION_PLAN_ID", referencedColumnName = "PRODUCTION_PLAN_ID")
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
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

    public void process() {
        Map<Integer, LocalDateTime> endDates = new HashMap<>();
        Map<Integer, LocalDateTime> endDates2 = new HashMap<>();
        Map<Integer, LocalDateTime> startDates = new HashMap<>();
        LocalDateTime dateTime = start;


        Item item = new Item();
        for(Order order : this.getOrders()) {
            for(OrderProduct orderProduct : order.getOrderProducts()) {
                for(ProductOperation productOperation : orderProduct.getProduct().getProductOperations()) {
                    if(!endDates.containsKey(productOperation.getResourceId())) {
                        endDates2.put(productOperation.getResourceId(), dateTime);
                    }
                }
            }
        }

        for (Order order : this.getOrders()) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                for (ProductOperation productOperation : orderProduct.getProduct().getProductOperations()) {
                    startDates.put(productOperation.getResourceId(), dateTime);
                    item.setResourceId(productOperation.getResourceId());
                    item.setGroupId(this.getGroupId());
                    item.setProductionPlanId(this.getId());
                    item.setStart(dateTime);
                    dateTime = dateTime.plus(productOperation.getDuration(), ChronoField.MILLI_OF_SECOND.getBaseUnit());
                    endDates.put(productOperation.getResourceId(), dateTime);
                    item.setEnd(dateTime);
                    items.add(item);
                }
                long diff = Long.MAX_VALUE;
                for (Map.Entry<Integer, LocalDateTime> entry : endDates2.entrySet()) {
                    if (startDates.containsKey(entry.getKey())) {
                        long diffTmp = Duration.between(startDates.get(entry.getKey()), entry.getValue()).toMillis();
                        if (diffTmp < diff) {
                            diff = diffTmp;
                        }
                    }
                }
                dateTime = dateTime.minusNanos(diff);
                endDates2 = endDates;
                startDates.clear();
            }
        }
    }
}
