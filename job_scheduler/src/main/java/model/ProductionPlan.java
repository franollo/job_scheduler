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
    @JoinColumn(name = "PRODUCTION_PLAN_ID", referencedColumnName = "PRODUCTION_PLAN_ID")
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

    public void process() {
        String[] classNames = {"red", "orange", "magneta", "blue", "green", "grey", "pink"};
        int index = 0;
        LocalDateTime dateTime = start;
        List<Item> items = new LinkedList<>();
        Map<Integer, LocalDateTime> endDates = new HashMap<>();
        Map<Integer, LocalDateTime> startDates = new HashMap<>();
        Map<Integer, LocalDateTime> endDates2 = new HashMap<>();
        List<Integer> resourceIds = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for(Order order : orders) {
            for(OrderProduct orderProduct : order.getOrderProducts()) {
                for(int i = 0; i < orderProduct.getAmount(); i++) {
                    products.add(orderProduct.getProduct());
                }
            }
        }
        for(Product product : products) {
            resourceIds.addAll(product.getProductOperations().stream().map(ProductOperation::getResourceId).collect(Collectors.toList()));
        }
        Set<Integer> uniqueResourceIds = new HashSet<>(resourceIds);
        for(Integer id : uniqueResourceIds) {
            endDates2.put(id, start);
        }
        for(Product product : products) {
            List<Item> tempItems = new LinkedList<>();
            for(ProductOperation productOperation : product.getProductOperations()) {
                if(productOperation.getDuration().getSeconds() > 0) {
                    startDates.put(productOperation.getResourceId(), dateTime);
                    dateTime = dateTime.plus(productOperation.getDuration());
                    endDates.put(productOperation.getResourceId(), dateTime);
                    tempItems.add(new Item(startDates.get(productOperation.getResourceId()),
                            endDates.get(productOperation.getResourceId()),
                            classNames[index % classNames.length],
                            productOperation.getResourceId(),
                            product.getId(),
                            this.getId(),
                            this.getGroupId()));
                }
            }
            long diff = Long.MAX_VALUE;
            for (Map.Entry<Integer, LocalDateTime> entry : endDates2.entrySet()) {
                if (startDates.containsKey(entry.getKey())) {
                    long diffTmp = Duration.between(entry.getValue(), startDates.get(entry.getKey())).toNanos();
                    if (diffTmp < diff) {
                        diff = diffTmp;
                    }
                }
            }
            for(Item item : tempItems) {
                item.setStart(item.getStart().minusNanos(diff));
                item.setEnd(item.getEnd().minusNanos(diff));
                endDates2.put(item.getResourceId(), item.getEnd());
            }
            items.addAll(tempItems);
            tempItems.clear();
            startDates.clear();
            endDates.clear();
            index++;
        }
        setItems(items);
    }
}
