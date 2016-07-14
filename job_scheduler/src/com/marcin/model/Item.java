package com.marcin.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "ITEMS")
//@AssociationOverrides({
//        @AssociationOverride(name = "productionPlan", joinColumns = @JoinColumn(name = "PRODUCTION_PLAN")),
//        @AssociationOverride(name = "resource", joinColumns = @JoinColumn(name = "RESOURCES"))
//})
public class Item {
    private int itemId;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime preparationStart;
    private LocalDateTime createdOn;
    private LocalDateTime editedOn;
    private Resource resource;
    private ProductionPlan productionPlan;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    @Column(name = "CREATED_ON")
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "EDITED_ON")
    public LocalDateTime getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(LocalDateTime editedOn) {
        this.editedOn = editedOn;
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

    public Date convertFromTask(Task task, Job job, Date startDate, Long diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MILLISECOND, (int) -diff);
        Date start = calendar.getTime();
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // this.startDate = formater.format(start);
        calendar.add(Calendar.SECOND, task.getSecondsDuration());
        Date end = calendar.getTime();
//        this.endDate = formater.format(end);
//        this.resource = task.getResource();
//        this.job = job;
        return end;
    }


}
