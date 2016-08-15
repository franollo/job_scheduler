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

function ProductOperation(name, description, duration, prepDuration, resource) {
    this.name = name;
    this.description = description;
    this.duration = duration;
    this.preparationDuration = prepDuration;
    this.resource = resource;
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
        switch(productOperation.duration.length) {
            case 2:
                productOperation.duration = Number(productOperation.duration);
                break;
            case 5:
                var seconds = Number(productOperation.duration.slice(3, 5));
                var minutes = Number(productOperation.duration.slice(0, 2));
                productOperation.duration = seconds + 60 * minutes;
                break;
            case 8:
                var seconds = Number(productOperation.duration.slice(6, 8));
                var minutes = Number(productOperation.duration.slice(3, 5));
                var hours = Number(productOperation.duration.slice(0, 2));
                productOperation.duration = seconds + 60 * minutes + 360 * hours;
                break;
            default:
                productOperation.duration = 0;
        }

        switch(productOperation.preparationDuration.length) {
            case 2:
                productOperation.preparationDuration = Number(productOperation.preparationDuration);
                break;
            case 5:
                var seconds = Number(productOperation.preparationDuration.slice(3, 5));
                var minutes = Number(productOperation.preparationDuration.slice(0, 2));
                productOperation.preparationDuration = seconds + 60 * minutes;
                break;
            case 8:
                var seconds = Number(productOperation.preparationDuration.slice(6, 8));
                var minutes = Number(productOperation.preparationDuration.slice(3, 5));
                var hours = Number(productOperation.preparationDuration.slice(0, 2));
                productOperation.preparationDuration = seconds + 60 * minutes + 360 * hours;
                break;
            default:
                productOperation.preparationDuration = 0;
        }
    });
}