package main.java.dao.model;

import main.java.model.Item;
import main.java.model.User;

import java.util.List;

public interface ItemDAO {
    public Item save(Item item);
    public void update(Item item);
    public void remove(Item item);
    public List<Item> getUserItems(User user);
    public void removeByPlanId(Integer productionPlanId);
}
