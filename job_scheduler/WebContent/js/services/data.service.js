angular.module('app').service('dataService', function ($http) {
    var vm = this;
    var httpError;
    var user;
    var resources;
    var jobs;
    var orders;
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
    vm.getErrors = getErrors;
    vm.flushErrors = flushErrors;
    vm.serviceGetResources = serviceGetResources;
    vm.serviceGetJobs = serviceGetJobs;
    vm.serviceGetOrders = serviceGetOrders;
    vm.serviceGetUser = serviceGetUser;

    function serviceGetOrders() {
        return orders;
    }

    function serviceGetJobs() {
        return jobs;
    }

    function serviceGetResources() {
        return resources;
    }

    function serviceGetUser() {
        return user;
    }

    function newOrder(order) {
        return post("data/neworder", order);
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
        resources = get("getdata/resources");
    }

    function getJobs() {
        jobs = get("getdata/jobs");
    }

    function getOrders() {
        orders = get("getdata/orders");
    }

    function whoAmI() {
        user = get("getdata/userinfo");
    }

    function openOrder(orderId) {
        return post("getdata/order", orderId);
    }

    function errorTest() {
        return get("http://localhost:3000/posts/1");
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
            httpError = {code: error.status, text: error.statusText};
            return undefined;
        })
    }

    function get(URL) {
        return $http({
            method: 'GET',
            url: URL,
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            httpError = {code: error.status, text: error.statusText};
            return undefined;
        })
    }

    function getErrors() {
        return httpError;
    }

    function flushErrors() {
        httpError = undefined;
    }
});