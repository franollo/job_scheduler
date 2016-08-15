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

    this.newResourceCtrl = function ($mdDialog, resourceTypes) {
        var vm = this;
        vm.resourceTypes = resourceTypes;
        vm.name = '';
        vm.description = '';
        vm.costPerHour = '';
        vm.efficiency = '';
        vm.resourceTypeId = '';
        vm.synchronous = false;
        vm.cancel = cancel;
        vm.answer = answer;
        function answer() {
            $mdDialog.hide(new Resource(vm.name, vm.description, vm.costPerHour, vm.efficiency, vm.resourceTypeId));
        }

        function cancel() {
            $mdDialog.cancel();
        }
    };

    this.newProductCtrl = function ($mdDialog, resourceTypes) {
        var vm = this;
        vm.resourceTypes = resourceTypes;
        vm.productOperations = [];
        vm.productName = '';
        vm.productDescription = '';
        vm.productAttr1 = '';
        vm.productAttr2 = '';
        vm.productAttr3 = '';
        vm.style = 'standard';
        vm.name = '';
        vm.description = '';
        vm.duration = '00:00:00';
        vm.prepDuration = '00:00:00';
        vm.resource = {};
        vm.cancel = cancel;
        vm.answer = answer;
        vm.addProductOperation = addProductOperation;
        vm.setStyle = setStyle;

        function setStyle(style) {
            vm.style = style;
        }

        function addProductOperation() {
            vm.productOperations.push(new ProductOperation(vm.name, vm.description, vm.duration, vm.prepDuration, vm.resource));
            vm.name = '';
            vm.description = '';
            vm.duration = '00:00:00';
            vm.prepDuration = '00:00:00';
            vm.resource = {};
        }
        
        function answer() {
            $mdDialog.hide(new Product(vm.productName, vm.productDescription, vm.productAttr1, vm.productAttr2, vm.productAttr3, vm.productOperations));
        }

        function cancel() {
            $mdDialog.cancel();
        }
    };

    this.editProductCtrl = function ($mdDialog, resourceTypes, product) {
        var vm = this;
        vm.resourceTypes = resourceTypes;
        vm.product = product;
        vm.style = 'standard';
        vm.name = '';
        vm.description = '';
        vm.duration = '00:00:00';
        vm.prepDuration = '00:00:00';
        vm.resource = {};
        vm.cancel = cancel;
        vm.answer = answer;
        vm.addProductOperation = addProductOperation;
        vm.removeProductOperation = removeProductOperation;
        vm.setStyle = setStyle;

        function setStyle(style) {
            vm.style = style;
        }

        function removeProductOperation(productOperation) {
            var index = vm.product.productOperations.map(function(e) {return e.$$hashKey}).indexOf(productOperation.$$hashKey);
            if(index >= 0) {
                vm.product.productOperations.splice(index, 1);
            }
        }

        function addProductOperation() {
            vm.product.productOperations.push(new ProductOperation(vm.name, vm.description, vm.duration, vm.prepDuration, vm.resource));
            console.log(vm.product.productOperations);
            vm.name = '';
            vm.description = '';
            vm.duration = '00:00:00';
            vm.prepDuration = '00:00:00';
            vm.resource = {};
        }

        function answer() {
            vm.product.productOperations.forEach(function (productOperation) {
                productOperation.resourceId = productOperation.resource.id;
                delete productOperation.resource;
                delete productOperation.$$hashKey;
                switch (productOperation.duration.length) {
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

                switch (productOperation.preparationDuration.length) {
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
            $mdDialog.hide(vm.product);
        }

        function cancel() {
            $mdDialog.cancel();
        }
    };

    this.editResourceCtrl = function ($mdDialog, resource) {
        var vm = this;
        vm.resource = {};
        angular.copy(resource, vm.resource);
        vm.cancel = cancel;
        vm.answer = answer;
        function answer() {
            $mdDialog.hide(vm.resource);
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