package main.java.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marcin Frankowski on 14.07.2016.
 */

@Entity
@Table(name = "ORDER_PRODUCTS")
public class OrderProduct implements Serializable{
    private Product product;
    private Order order;
    private int amount;
    private int groupId;

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", updatable = false, nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", updatable = false, nullable = false)
    @JsonBackReference
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "AMOUNT")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "GROUP_ID")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof OrderProduct){
            OrderProduct temp = (OrderProduct)o;
            if (this.product.getId().equals(temp.getProduct().getId())) {
                return true;
            }
        }
        return false;
    }

    public void addAmount(int toAdd) {
        this.amount = this.amount + toAdd;
    }
}
