package com.marcin.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.marcin.model.Item;
import com.marcin.model.VisItem;

public interface ItemDAO {
	public void createNewItem(Item item, int orderId, int itemId);
	public void createNewItem(Item item, int orderId);
	public List<VisItem> getOrderItems(int orderId);
	public void updateItem(VisItem item, String name);
	public Map<Integer, Date> getEndDates(String name);
	public Date getMaxDate(String name);
}
