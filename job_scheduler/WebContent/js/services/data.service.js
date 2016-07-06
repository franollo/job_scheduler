angular
    .module('app')
    .service('dataService', dataService);

function dataService($http) {
    var vm = this;
    vm.whoAmI = whoAmI;
    vm.newOrder = newOrder;
    vm.updateOrder = updateOrder;
    vm.updateItem = updateItem;
    vm.newJob = newJob;
    vm.deleteJob = deleteJob;
    vm.updateJob = updateJob;
    vm.deleteResource = deleteResource;
    vm.updateResource = updateResource;
    vm.addJobOrder = addJobOrder;
    vm.newResource = newResource;
    vm.getResources = getResources;
    vm.getJobs = getJobs;
    vm.getOrders = getOrders;
    vm.openOrder = openOrder;
    vm.errorTest = errorTest;
    vm.newPerson = newPerson;
    function newOrder(order) {
        return post("data/neworder", order);
    }

    function newPerson(order) {
        return post("data/person", order);
    }

    function updateOrder(order) {
        return post("data/updateorder", order);
    }

    function updateItem(item) {
        return post("data/updateitem", item);
    }

    function newJob(job) {
        return post("data/newjob", job);
    }

    function deleteJob(job) {
        return post("data/deletejob", job);
    }

    function updateJob(job) {
        return post("data/updatejob", job);
    }

    function deleteResource(resource) {
        return post("data/deleteresource", resource);
    }

    function updateResource(resource) {
        return post("data/updateresource", resource);
    }

    function addJobOrder(job) {
        return post("data/addjob", job);
    }

    function newResource(resource) {
        return post("data/newresource", resource);
    }

    function getResources() {
        return get("getdata/resources");
    }

    function getJobs() {
        return get("getdata/jobs");
    }

    function getOrders() {
        return get("getdata/orders");
    }

    function whoAmI() {
        return get("getdata/userinfo");
    }

    function openOrder(orderId) {
        return get("getdata/order?" + orderId);
    }

    function errorTest() {
        return get("http://localhost:3000/posts/2");
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