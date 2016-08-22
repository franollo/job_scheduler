package main.java.dao.model;

import main.java.model.Item;
import main.java.model.User;

import java.util.List;

public interface ItemDAO {
    public void save(Item item);
    public void update(Item item);
    public void remove(Item item);
    public List<Item> getUserItems(User user);
    public void removeByPlanId(Integer productionPlanId);
  //  public boolean checkGroupId(int itemId, int groupId);

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
