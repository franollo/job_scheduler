package main.java.com.dao.model;

import main.java.com.model.Item;

public interface ItemDAO {
    public void insert(Item item);
    public void update(Item item);
    public void delete(Item item);
    public boolean checkGroupId(int itemId, int groupId);

//    public void createNewItem(Item item, int orderId, int itemId);
//
//    public void createNewItem(Item item, int orderId);
//
//    public List<VisItem> getOrderItems(int orderId);
//
//    public void updateItem(VisItem item, String name);
//
//    public Map<Integer, Date> getEndDates(String name);
//
//    public Date getMaxDate(String name);
}
