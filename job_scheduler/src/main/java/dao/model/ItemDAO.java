package main.java.dao.model;

import main.java.model.Item;
import main.java.model.User;

import java.util.List;

/**
 * DAO interface for Item class
 * @see main.java.model.Item
 * @author Marcin Frankowski
 */

public interface ItemDAO {
    /**
     * Update item in database or insert if not exists
     * @param item entity to save
     * @return entity saved in database
     */
    public Item save(Item item);

    /**
     * Remove item from database
     * @param  item item to remove
     */
    public void remove(Item item);

    /**
     * Get items to which user has permission from database
     * @param user given user
     * @return list of user's Items
     */
    public List<Item> getUserItems(User user);

    /**
     * Remove items that belong to production plan with given id
     * @param productionPlanId given production plan id
     */
    public void removeByPlanId(Integer productionPlanId);
}
