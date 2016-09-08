package main.java.dao.model;

import main.java.model.ResourceType;
import main.java.model.User;

import java.util.List;

/**
 * DAO interface for ResourceType class
 * @see main.java.model.ResourceType
 * @author Marcin Frankowski
 */

public interface ResourceTypeDAO {

    /**
     * Update resource type in database or insert if not exists
     * @param resourceType entity to save
     * @return entity saved in database
     */
    public ResourceType save(ResourceType resourceType);

    /**
     * Remove resource type with given id from database
     * @param id id of resource type to remove
     */
    public void remove(Integer id);

    /**
     * Remove resource types with given list of id from database
     * @param ids list of ids of resource types to remove
     */
    public void multipleRemove(List<Integer> ids);

    /**
     * Get resource types to which user has permission from database
     * @param user given user
     * @return list of user's resource types
     */
    public List<ResourceType> getUsersResourceTypes(User user);
}
