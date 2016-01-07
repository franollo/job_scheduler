package com.marcin.dao;

import java.util.Date;

public interface OrderDAO {
	public int createNewOrder(String orderName, Date startDate);
}
