package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ITEMS")
public class Item extends GroupObject {
//    private int itemId;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime preparationStart;
    private Resource resource;
    private ProductionPlan productionPlan;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ITEM_ID")
//    public int getItemId() {
//        return itemId;
//    }
//
//    public void setItemId(int itemId) {
//        this.itemId = itemId;
//    }

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

    @Column(name = "PREPARATION_START")
    public LocalDateTime getPreparationStart() {
        return preparationStart;
    }

    public void setPreparationStart(LocalDateTime preparationStart) {
        this.preparationStart = preparationStart;
    }

    @ManyToOne
    @JoinColumn(name = "RESOURCE_ID")
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @ManyToOne
    @JoinColumn(name = "PRODUCTION_PLAN_ID")
    public ProductionPlan getProductionPlan() {
        return productionPlan;
    }

    public void setProductionPlan(ProductionPlan productionPlan) {
        this.productionPlan = productionPlan;
    }
}
