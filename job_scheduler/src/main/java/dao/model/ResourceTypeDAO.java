package main.java.dao.model;

import main.java.model.ResourceType;
import main.java.model.User;

import java.util.List;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface ResourceTypeDAO {
    public void insert(ResourceType resourceType);
    public void update(ResourceType resourceType);
    public void delete(ResourceType resourceType);
    public List<ResourceType> getUsersResourceTypes(User user);
}
