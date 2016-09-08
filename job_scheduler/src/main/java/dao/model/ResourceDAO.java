package main.java.dao.model;

import main.java.model.Resource;
import main.java.model.User;

import java.util.List;

/**
 * DAO interface for Resource class
 * @see main.java.model.Resource
 * @author Marcin Frankowski
 */

public interface ResourceDAO {
    /**
     * Update resource in database or insert if not exists
     * @param resource entity to save
     * @return entity saved in database
     */
    public Resource save(Resource resource);

    /**
     * Remove resource from database
     * @param  resource resource to remove
     */
    public void remove(Resource resource);

    /**
     * Remove resources with given list of ids from database
     * @param ids list of ids of resource types to remove
     */
    public void multipleRemove(List<Integer> ids);

    /**
     * Get resources to which user has permission from database
     * @param user given user
     * @return list of user's resources
     */
    public List<Resource> getUsersResources(User user);
}
