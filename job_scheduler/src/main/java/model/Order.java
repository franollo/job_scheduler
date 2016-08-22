package main.java.model;

import main.java.model.common.GroupObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@AttributeOverride(name = "id", column = @Column(name = "ORDER_ID"))
public class Order extends GroupObject {
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private Integer productionPlanId;
    private List<OrderProduct> orderProducts;

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

    @Column(name = "DUE_DATE")
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Column(name = "PRODUCTION_PLAN_ID")
    public Integer getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(Integer productionPlanId) {
        this.productionPlanId = productionPlanId;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);
        orderProducts.add(orderProduct);
    }

    public void mergeDuplicatedOrderProducts() {
        int index;
        List<OrderProduct> tempList = new ArrayList<>();
        for(OrderProduct orderProduct : orderProducts) {
            index = tempList.indexOf(orderProduct);
            if(index > -1) {
                tempList.get(index).addAmount(orderProduct.getAmount());
            }
            else {
                tempList.add(orderProduct);
            }
        }
        this.orderProducts = new LinkedList<>(tempList);
    }
}
