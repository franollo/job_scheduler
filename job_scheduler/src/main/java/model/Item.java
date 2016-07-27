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
    private int resourceId;
    private int productionPlanId;

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

    @Column(name = "RESOURCE_ID")
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Column(name = "PRODUCTION_PLAN_ID")
    public int getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(int productionPlanId) {
        this.productionPlanId = productionPlanId;
    }
}
