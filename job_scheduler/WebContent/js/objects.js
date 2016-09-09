/**
 * Created by Marcin Frankowski on 09.08.16.
 */

function ResourceType(name, description) {
    this.name = name;
    this.description = description;
    this.resources = [];
}

function Resource(name, description, costPerHour, efficiency, resourceTypeId) {
    this.name = name;
    this.description = description;
    this.costPerHour = costPerHour;
    this.efficiency = efficiency;
    this.resourceTypeId = resourceTypeId;
}

function ProductOperation(name, description, duration, prepDuration, resourceId) {
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.preparationDuration = prepDuration;
    this.resourceId = resourceId;
    this.sequential = true;
}

function Product(name, description, attribute1, attribute2, attribute3, productOperations) {
    this.name = name;
    this.description = description;
    this.attribute1 = attribute1;
    this.attribute2 = attribute2;
    this.attribute3 = attribute3;
    this.productOperations = productOperations;
    this.id = null;
    this.createdOn = null;
    this.editedOn = null;
    this.setId = function(id) {
        this.id = id;
    };
    this.setCreatedOn = function(createdOn) {
        this.createdOn = createdOn;
    };
    this.setEditedOn = function(editedOn) {
        this.editedOn = editedOn;
    };
}

function Order(name, description, dueDate, orderProducts) {
    this.name = name;
    this.description = description;
    this.dueDate = dueDate;
    this.orderProducts = orderProducts;
    this.setId = function(id) {
        this.id = id;
    };
    this.setCreatedOn = function(createdOn) {
        this.createdOn = createdOn;
    };
    this.setEditedOn = function(editedOn) {
        this.editedOn = editedOn;
    };
    this.setDueDate = function(dueDate) {
        this.dueDate = dueDate;
    }
}

function OrderProduct(product, amount) {
    this.product = product;
    this.amount = amount;
}

function ProductionPlan(name, startDate, orders) {
    this.name = name;
    this.start = startDate;
    this.orders = orders;
    this.setId = function(id) {
        this.id = id;
    };
    this.setCreatedOn = function(createdOn) {
        this.createdOn = createdOn;
    };
    this.setEditedOn = function(editedOn) {
        this.editedOn = editedOn;
    };
}

