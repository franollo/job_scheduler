package main.java.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import main.java.model.common.GroupObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PRODUCTS")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
public class Product extends GroupObject {
    private String name;
    private String description;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private Set<ProductOperation> productOperations;
    private Set<OrderProduct> orderProducts;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ATTRIBUTE_1")
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    @Column(name = "ATTRIBUTE_2")
    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    @Column(name = "ATTRIBUTE_3")
    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    public Set<ProductOperation> getProductOperations() {
        return productOperations;
    }

    public void setProductOperations(Set<ProductOperation> productOperations) {
        this.productOperations = productOperations;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    @OneToMany
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
