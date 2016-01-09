package com.marcin.dao;

import java.util.List;

import com.marcin.model.Resource;

public interface ResourceDAO 
{
	public void insert(Resource machine);
	public Resource getByID(int machineID);
	public List<Resource> getUserResources(String username);
}
