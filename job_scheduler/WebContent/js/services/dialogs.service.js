angular
    .module('app')
    .service('dialogsService', dialogsService);

function dialogsService(dataService) {
    var vm = this;
    vm.openProductionPlanController = openProductionPlanController;

        this.DialogController1 = function ($scope, $mdDialog, dataToPass) {
        $scope.dragControlListeners = {
            containment: '#board',//optional param.
            allowDuplicates: true //optional param allows duplicates to be dropped.
        };
        this.tasks = dataToPass;
        var sth = false;
        $scope.answer = function () {
            $scope.job.tasks = JSON.parse(JSON.stringify($scope.selected));
            $mdDialog.hide({"job": $scope.job});
            $scope.job = {};
            $scope.selected = [];
        }

        $scope.cancel = function () {
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


    this.newResourceTypeCtrl = function ($mdDialog) {
        var vm = this;
        vm.name = '';
        vm.description = '';
        vm.cancel = cancel;
        vm.answer = answer;
        function answer() {
            $mdDialog.hide(new ResourceType(vm.name, vm.description));
        }

        function cancel() {
            $mdDialog.cancel();
        }
    };

    this.editResourceTypeCtrl = function ($mdDialog, resourceType) {
        var vm = this;
        vm.resourceType = {};
        angular.copy(resourceType, vm.resourceType);
        vm.cancel = cancel;
        vm.answer = answer;
        function answer() {
            $mdDialog.hide(vm.resourceType);
        }

        function cancel() {
            $mdDialog.cancel();
        }
    };

    this.addJobOrderController = function ($scope, $mdDialog, dataToPass) {
        var parentThis = this;
        this.jobs = dataToPass;
        this.selected = [];
        $scope.answer = function () {
            $mdDialog.hide(parentThis.selected);
        }

        $scope.cancel = function () {
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

    this.editOrderController = function ($scope, $mdDialog, data) {
        var parentThis = this;
        $scope.order = data;
        $scope.answer = function () {
            $mdDialog.hide($scope.order);
        }

        $scope.cancel = function () {
            $mdDialog.cancel();
        }
    }

    this.DialogController4 = function ($scope, $mdDialog) {
        var parentThis = this;
        this.resourceName = "";
        this.description = "";
        $scope.answer = function () {
            $mdDialog.hide({
                "name": parentThis.resourceName,
                "description": parentThis.description
            });
        }
        $scope.cancel = function () {
            $mdDialog.cancel();
        }
    }

    function openProductionPlanController($scope, $mdDialog) {
        var vm = this;
        vm.answer = answer;
        vm.cancel = cancel;
        vm.select = select;
        vm.productionPlans = [];
        vm.selectedPlanId = 0;
        dataService.getProductionPlans().then(function (data) {
            for (var i = 0; i < data.length; i++) {
                if (!$scope.$$phase) {
                    logic(data[i]);
                } else {
                    $timeout(logic(data[i]));
                }
            }
        });

        function logic(item) {
            vm.productionPlans.push(item);
        }

        function answer() {
            $mdDialog.hide(vm.selectedPlanId);
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function select(id) {
            vm.selectedPlanId = id;
        }
    }
}