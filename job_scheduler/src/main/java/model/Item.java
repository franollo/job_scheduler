package main.java.model;

import main.java.model.common.GroupObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ITEMS")
@AttributeOverride(name = "id", column = @Column(name = "ITEM_ID"))
public class Item extends GroupObject {
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime preparationStart;
    private Resource resource;
    private ProductionPlan productionPlan;

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
