angular
    .module('app')
    .service('dataService', dataService);

function dataService($http) {
    var vm = this;
    vm.saveResource = saveResource;
    vm.saveResourceType = saveResourceType;
    vm.saveProductionPlan = saveProductionPlan;
    vm.saveProduct = saveProduct;
    vm.saveOrder = saveOrder;
    vm.saveItem = saveItem;
    vm.removeResource = removeResource;
    vm.removeResourceType = removeResourceType;
    vm.removeResources = removeResources;
    vm.removeResourceTypes = removeResourceTypes;
    vm.removeProductionPlan = removeProductionPlan;
    vm.removeProduct = removeProduct;
    vm.removeProducts = removeProducts;
    vm.removeOrder = removeOrder;
    vm.removeItem = removeItem;
    vm.getResources = getResources;
    vm.getProductionPlans = getProductionPlans;
    vm.getProductionPlan = getProductionPlan;
    vm.getProducts = getProducts;
    vm.getOrders = getOrders;
    vm.getUserInfo = getUserInfo;

    function saveResource(resource) {
        return post("save/resource", resource);
    }

    function saveResourceType(resourceType) {
        return post("save/resourcetype", resourceType);
    }

    function saveProductionPlan(productionPlan) {
        return post("save/productionplan", productionPlan);
    }

    function saveProduct(product) {
        return post("save/product", product);
    }

    function saveOrder(order) {
        return post("save/order", order);
    }

    function saveItem(item) {
        return post("save/item", item);
    }

    function removeResource(id) {
        return get("remove/resource/" + id);
    }

    function removeResourceType(id) {
        return get("remove/resourcetype/" + id);
    }

    function removeResources(ids) {
        return post("remove/resources", ids);
    }

    function removeResourceTypes(ids) {
        return post("remove/resourcetypes", ids);
    }

    function removeProductionPlan(productionPlan) {
        return post("remove/productionplan", productionPlan);
    }

    function removeProduct(id) {
        return get("remove/product/" + id);
    }

    function removeProducts(ids) {
        return post("remove/products", ids);
    }

    function removeOrder(order) {
        return post("remove/order", order);
    }

    function removeItem(item) {
        return post("remove/item", item);
    }

    function getResources() {
        return get("get/resources");
    }

    function getProductionPlans() {
        return get("get/productionplans");
    }

    function getProductionPlan(productionPlanId) {
        return get("get/productionplan/" + productionPlanId);
    }

    function getProducts() {
        return get("get/products");
    }

    function getOrders() {
        return get("get/orders");
    }

    function getUserInfo() {
        return get("get/userinfo");
    }

    function post(URL, data) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: URL,
            data: JSON.stringify(data)
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            throw error;
        })
    }

    function get(URL) {
        return $http({
            method: 'GET',
            url: URL
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            throw error;
        })
    }
}