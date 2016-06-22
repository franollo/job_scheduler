package com.marcin.model;

public class Resource {
    private int resourceId;
    private String name;
    private String description;

    public Resource(int resourceId, String name, String description) {
        this.setResourceId(resourceId);
        this.setName(name);
        this.setDescription(description);
    }

    public Resource() {

    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceID) {
        this.resourceId = resourceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
