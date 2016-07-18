package com.marcin.dao;

import com.marcin.model.Resource;

public interface ResourceDAO {
    public void insert(Resource resource);
    public void update(Resource resource);
    public void delete(Resource resource);
    public boolean checkGroupId(int resourceId, int groupId);

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
