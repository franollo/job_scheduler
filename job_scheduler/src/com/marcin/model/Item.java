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
	
	public Date convertFromTask(Task task, Job job, Date startDate) {
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(task.getResourceId() + "   " + job.getJobId());
		System.out.println(startDate);
		this.startDate = formater.format(startDate);
		System.out.println(this.startDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.SECOND, task.getSecondsDuration());
		System.out.println(task.getSecondsDuration());
		System.out.println(calendar.getTime());
		Date end = calendar.getTime();
		System.out.println(end);
		this.endDate = formater.format(end);
		System.out.println(endDate);
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
