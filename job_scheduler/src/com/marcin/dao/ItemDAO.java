package com.marcin.dao;

import java.util.List;

import com.marcin.model.Item;
import com.marcin.model.VisItem;

public interface ItemDAO {
	public void createNewItem(Item item, int orderId, int itemId);
	public List<VisItem> getOrderItems(int orderId);
	public void updateItem(VisItem item, String name);
}
