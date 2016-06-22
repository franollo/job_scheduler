package com.marcin.model;

import java.util.List;

public class Job {
    private int jobId;
    private String name;
    private String description;
    private boolean order;
    private List<Task> tasks;

    public Job(String name, String description, boolean order, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.order = order;
        this.tasks = tasks;
    }

    public Job(int jobId, String name, String description, boolean order, List<Task> tasks) {
        this.jobId = jobId;
        this.name = name;
        this.description = description;
        this.order = order;
        this.tasks = tasks;
    }

    public Job() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
