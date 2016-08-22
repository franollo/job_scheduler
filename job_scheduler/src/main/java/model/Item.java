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
    private String className;
    private Integer resourceId;
    private Integer productId;
    private Integer productionPlanId;

    public Item(){}

    public Item(LocalDateTime start,
                LocalDateTime end,
                String className,
                Integer resourceId,
                Integer productId,
                Integer productionPlanId,
                Integer groupId) {
        this.start = start;
        this.end = end;
        this.className = className;
        this.resourceId = resourceId;
        this.productId = productId;
        this.productionPlanId = productionPlanId;
        setGroupId(groupId);
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

    @Column(name = "PREPARATION_START")
    public LocalDateTime getPreparationStart() {
        return preparationStart;
    }

    public void setPreparationStart(LocalDateTime preparationStart) {
        this.preparationStart = preparationStart;
    }

    @Column(name = "COLOR")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column(name = "RESOURCE_ID")
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Column(name = "PRODUCT_ID")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Column(name = "PRODUCTION_PLAN_ID")
    public Integer getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(Integer productionPlanId) {
        this.productionPlanId = productionPlanId;
    }
}
