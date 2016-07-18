package main.java.com.dao.model;

import main.java.com.model.ResourceType;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface ResourceTypeDAO {
    public void insert(ResourceType resourceType);
    public void update(ResourceType resourceType);
    public void delete(ResourceType resourceType);
    public boolean checkGroupId(int resourceTypeId, int groupId);
}
