package com.marcin.model;

import java.util.Calendar;
import java.util.Date;

public class Item {
	private int itemId;
	private String startDate;
	private String endDate;
	private Resource resource;
	private Job job;
	private String color;
	
	public Item(){
		
	}
	
	public Date convertFromTask(Task task, Job job, Date startDate, Long diff) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MILLISECOND, (int)-diff);
		Date start = calendar.getTime();
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.startDate = formater.format(start);
		calendar.add(Calendar.SECOND, task.getSecondsDuration());
		Date end = calendar.getTime();
		this.endDate = formater.format(end);
		this.resource = task.getResource();
		this.job = job;
		return end;
	}
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
