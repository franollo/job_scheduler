package com.marcin.model;

public class Machine 
{
	private int machineID;
	private String name;
	private int groupID;

	public Machine(int i, String string, int j) 
	{
		machineID = i;
		name = string;
		groupID = j;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public int getGroupID() 
	{
		return groupID;
	}
	public void setGroupID(int groupID) 
	{
		this.groupID = groupID;
	}
	public int getMachineID() 
	{
		return machineID;
	}
	public void setMachineID(int machineID) 
	{
		this.machineID = machineID;
	}
}
