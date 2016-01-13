package com.marcin.model;

import java.util.List;

public class VisContent {
	private List<VisItem> items;
	private List<VisGroup> groups;
	
	public VisContent(List<VisItem> items, List<VisGroup> groups) {
		this.items = items;
		this.groups = groups;
	}
	
	public List<VisItem> getItems() {
		return items;
	}
	public void setItems(List<VisItem> items) {
		this.items = items;
	}
	public List<VisGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<VisGroup> groups) {
		this.groups = groups;
	}
}
