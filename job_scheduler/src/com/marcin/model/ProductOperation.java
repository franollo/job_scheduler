package com.marcin.model;

import javax.persistence.*;
import java.time.*;

/**
 * Created by Marcin Frankowski on 12.07.2016.
 */
@Entity
@Table(name="PRODUCT_OPERATIONS")
public class ProductOperation {
    private int productOperationId;
    private int operationNumber;
    private String name;
    private Duration duration;
    private Duration preparationDuration;
    private Resource resource;
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_OPERATION_ID")
    public int getProductOperationId() {
        return productOperationId;
    }

    public void setProductOperationId(int productOperationId) {
        this.productOperationId = productOperationId;
    }

    @Column(name = "OPERATION_NUMBER")
    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DURATION")
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Column(name = "PREPARATION_DURATION")
    public Duration getPreparationDuration() {
        return preparationDuration;
    }

    public void setPreparationDuration(Duration preparationDuration) {
        this.preparationDuration = preparationDuration;
    }

    @ManyToOne
    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @ManyToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


