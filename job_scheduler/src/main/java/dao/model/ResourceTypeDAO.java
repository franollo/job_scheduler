package main.java.dao.model;

import main.java.model.ResourceType;
import main.java.model.User;

import java.util.List;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface ResourceTypeDAO {
    public ResourceType save(ResourceType resourceType);
    public void update(ResourceType resourceType);
    public void remove(Integer id);
    public void multipleRemove(List<Integer> ids);
    public List<ResourceType> getUsersResourceTypes(User user);
}
