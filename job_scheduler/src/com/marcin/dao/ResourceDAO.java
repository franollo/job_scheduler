package com.marcin.dao;

import java.util.List;

import com.marcin.model.Resource;
import com.marcin.model.VisGroup;

public interface ResourceDAO
{
	public Resource getByID(int resourceID);
	public List<Resource> getUserResources(String username);
	public void createNewResource(Resource resource, String name);
	public List<VisGroup> getOrderGroups(int orderId);
	void deleteResource(Resource resource, String name);
	void updateResource(Resource resource, String name);
}