angular
    .module('app')
    .service('dialogsService',['$http','dataService','authService',function($http,dataService, authService) {

    this.DialogController1 = function ($scope, $mdDialog, dataToPass) {
        $scope.dragControlListeners = {
            containment: '#board',//optional param.
            allowDuplicates: true //optional param allows duplicates to be dropped.
        };
        this.tasks = dataToPass;
        var sth = false;
        $scope.answer = function() {
            $scope.job.tasks = JSON.parse(JSON.stringify($scope.selected));
            $mdDialog.hide({"job": $scope.job});
            $scope.job = {};
            $scope.selected = [];
        }

        $scope.cancel = function() {
            $scope.job = {};
            $scope.selected = [];
            $mdDialog.cancel();
        }
        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) list.splice(idx, 1);
            else list.push(item);
        };
        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };
        $scope.selected = [];
        $scope.job = {};
    }


    this.newOrderController = function($scope, $mdDialog, dataToPass) {
        var parentThis = this;
        this.jobs = dataToPass;
        this.selected = [];
        this.startTime = new Date();
        this.startDate = new Date();
        this.startTime.setMilliseconds(0);
        this.startDate.setMilliseconds(0);
        $scope.answer = function() {
            var start = new Date(parentThis.startDate.getFullYear(), parentThis.startDate.getMonth(), parentThis.startDate.getDate(),
                parentThis.startTime.getHours(), parentThis.startTime.getMinutes(), parentThis.startTime.getSeconds());
            $scope.order.startDate = start;
            $scope.order.endDate = start;
            $scope.order.jobs = parentThis.selected;
            $mdDialog.hide($scope.order);
        }

        $scope.cancel = function() {
            console.log('cancel');
            $mdDialog.cancel();
        }
        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) list.splice(idx, 1);
            else list.push(item);
        };
        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };
    }

    this.addJobOrderController = function($scope, $mdDialog, dataToPass) {
        var parentThis = this;
        this.jobs = dataToPass;
        this.selected = [];
        $scope.answer = function() {
            $mdDialog.hide(parentThis.selected);
        }

        $scope.cancel = function() {
            console.log('cancel');
            $mdDialog.cancel();
        }
        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) list.splice(idx, 1);
            else list.push(item);
        };
        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };
    }

    this.editOrderController = function($scope, $mdDialog, dataToPass) {
        var parentThis = this;
        $scope.order = dataToPass;
        console.log($scope.order);
        this.startTime = new Date($scope.order.startDate);
        this.startDate = new Date($scope.order.startDate);
        $scope.answer = function() {
            var start = new Date(parentThis.startDate.getFullYear(), parentThis.startDate.getMonth(), parentThis.startDate.getDate(),
                parentThis.startTime.getHours(), parentThis.startTime.getMinutes(), parentThis.startTime.getSeconds());
            $scope.order.startDate = start;
            $mdDialog.hide($scope.order);
        }

        $scope.cancel = function() {
            $mdDialog.cancel();
        }
    }

    this.DialogController4 = function($scope, $mdDialog) {
        var parentThis = this;
        this.resourceName = "";
        this.description = "";
        $scope.answer = function() {
            $mdDialog.hide({"name": parentThis.resourceName,
                "description": parentThis.description});
        }
        $scope.cancel = function() {
            $mdDialog.cancel();
        }
    }

    this.openOrderController = function($scope, $mdDialog) {
        var parentThis = this;
        $scope.orders = [];
        $scope.selectedOrderId;
        data.getOrders().then(function(data) {
            for (var i = 0; i < data.length; i++) {
                if (!$scope.$$phase) {
                    logic(data[i]);
                } else {
                    $timeout(logic(data[i]));
                }
            }
            console.log($scope.orders);
        });
        $scope.answer = function() {
            $mdDialog.hide($scope.selectedOrderId);
        }

        $scope.cancel = function() {
            $mdDialog.cancel();
        }

        function logic(item) {
            $scope.orders.push(item);
        }

        $scope.select = function(id) {
            $scope.selectedOrderId = id;
        }
    }
}]);