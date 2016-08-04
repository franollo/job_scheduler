package main.java.dao.model;

import main.java.model.Resource;
import main.java.model.User;

import java.util.List;

public interface ResourceDAO {
    public void insert(Resource resource);
    public void update(Resource resource);
    public void remove(Resource resource);
    public List<Resource> getUsersResources(User user);
 //   public boolean checkGroupId(int resourceId, int groupId);

//    public Resource getByID(int resourceID);
//
//    public List<Resource> getUserResources(String username);
//
//    public void createNewResource(Resource resource, String name);
//
//    public List<VisGroup> getOrderGroups(int orderId);
//
//    void deleteResource(Resource resource, String name);
//
//    void updateResource(Resource resource, String name);
}
