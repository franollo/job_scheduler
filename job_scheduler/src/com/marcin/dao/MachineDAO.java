package com.marcin.dao;

import com.marcin.model.Machine;

public interface MachineDAO 
{
	public void insert(Machine machine);
	public Machine getByID(int machineID);
}
