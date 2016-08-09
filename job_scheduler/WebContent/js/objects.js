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