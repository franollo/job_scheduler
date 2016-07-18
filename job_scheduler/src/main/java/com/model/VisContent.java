package main.java.com.model;

import java.util.List;

public class VisContent {
    private List<VisItem> items;
    private List<VisGroup> groups;
    private Order order;

    public VisContent(List<VisItem> items, List<VisGroup> groups, Order order) {
        this.items = items;
        this.groups = groups;
        this.order = order;
    }

    public List<VisItem> getItems() {
        return items;
    }

    public void setItems(List<VisItem> items) {
        this.items = items;
    }

    public List<VisGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<VisGroup> groups) {
        this.groups = groups;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
