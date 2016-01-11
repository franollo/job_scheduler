package com.marcin.dao;

import java.util.List;

import com.marcin.model.Resource;

public interface ResourceDAO 
{
	public void createNewResource(Resource resource);
	public Resource getByID(int resourceID);
	public List<Resource> getUserResources(String username);
}
