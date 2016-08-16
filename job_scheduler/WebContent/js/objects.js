/**
 * Created by Marcin Frankowski on 09.08.16.
 */

function ResourceType(name, description) {
    this.name = name;
    this.description = description;
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
}

function Product(name, description, attribute1, attribute2, attribute3, productOperations) {
    this.name = name;
    this.description = description;
    this.attribute1 = attribute1;
    this.attribute2 = attribute2;
    this.attribute3 = attribute3;
    this.productOperations = productOperations;
    productOperations.forEach(function (productOperation) {
        delete productOperation.$$hashKey;
    });
}