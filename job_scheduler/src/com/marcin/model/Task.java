package com.marcin.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Task {
    private int resourceId;
    private String duration;
    private int secondsDuration;
    private Resource resource;

    public Task(int resourceId, String duration) {
        this.resourceId = resourceId;
        this.duration = duration;
    }

    public Task(int resourceId, int secondsDuration) {
        this.resourceId = resourceId;
        this.secondsDuration = secondsDuration;
    }

    public Task(int resourceId, String duration, Resource resource) {
        this.resourceId = resourceId;
        this.duration = duration;
        this.setResource(resource);
    }

    public Task() {
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int taskId) {
        this.resourceId = taskId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getSecondsDuration() {
        return secondsDuration;
    }

    public void setSecondsDuration(int secondsDuration) {
        this.secondsDuration = secondsDuration;
    }

    public void convertTimeToSeconds() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date parsedDate = formatter.parse(duration);
        calendar.setTime(parsedDate);
        secondsDuration = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
