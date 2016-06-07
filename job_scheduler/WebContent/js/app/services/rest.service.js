angular.module('app').service('restService', function($http) {
    var user = {};
    this.whoAmI = function() {
        return $http.get("getdata/userinfo").then(function(data) {
            user.username = data.data.login;
            user.name = data.data.name;
            user.surname = data.data.surname;
            return user;
        });
    };

    this.newOrder = function(order) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: "data/neworder",
            data: JSON.stringify(order)
        }).then(function(data){
            return data.data;
        });
    };

    this.updateOrder = function(order) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: "data/updateorder",
            data: JSON.stringify(order)
        }).then(function(data){
            return data.data;
        });
    };

    this.updateItem = function(item) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: "data/updateitem",
            data: JSON.stringify(item)
        }).then(function(data){
            return data.data;
        });
    };

    this.newJob = function(job) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: "data/newjob",
            data: JSON.stringify(job)
        }).then(function(data){
            return data.data;
        });
    };

    this.deleteJob = function(job) {
        $http({
            method: 'POST',
            dataType: 'json',
            url: "data/deletejob",
            data: JSON.stringify(job)
        }).then(function(data){
            return data.data;
        });
    };

    this.updateJob = function(job) {
        $http({
            method: 'POST',
            dataType: 'json',
            url: "data/updatejob",
            data: JSON.stringify(job)
        }).then(function(data){
            return data.data;
        });
    };

    this.deleteResource = function(resource) {
        $http({
            method: 'POST',
            dataType: 'json',
            url: "data/deleteresource",
            data: JSON.stringify(resource)
        }).then(function(data){
            return data.data;
        });
    };

    this.updateResource = function(resource) {
        $http({
            method: 'POST',
            dataType: 'json',
            url: "data/updateresource",
            data: JSON.stringify(resource)
        }).then(function(data){
            return data.data;
        });
    };

    this.addJobOrder = function(job) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: "data/addjob",
            data: JSON.stringify(job)
        }).then(function(data){
            return data.data;
        });
    };

    this.newResource = function(resource) {
        return $http({
            method: 'POST',
            dataType: 'json',
            url: "data/newresource",
            data: JSON.stringify(resource)
        }).then(function(data){
            return data.data;
        });
    };

    this.getResources = function() {
        return $http.get("getdata/resources").then(function(data){
            return data;
        });
    };

    this.getJobs = function() {
        return $http.get("getdata/jobs")
            .then(function(data){
                return data;
            });
    };

    this.getOrders = function() {
        return $http.get("getdata/orders")
            .then(function(data){
                return data.data;
            });
    };

    this.openOrder = function(orderId) {
        return $http({
            method: 'POST',
            url: "getdata/order",
            data: orderId
        }).then(function(data){
            return data.data;
        })
    };
});