angular
    .module('app')
    .service('dialogsService', dialogsService);

function dialogsService(dataService, $mdToast) {
    var vm = this;
    vm.openProductionPlanController = openProductionPlanController;
    vm.showErrorToast = showErrorToast;
    vm.newResourceTypeCtrl = newResourceTypeCtrl;
    vm.editResourceTypeCtrl = editResourceTypeCtrl;
    vm.newResourceCtrl = newResourceCtrl;
    vm.editResourceCtrl = editResourceCtrl;
    vm.newProductCtrl = newProductCtrl;
    vm.editProductCtrl = editProductCtrl;
    vm.newOrderCtrl = newOrderCtrl;
    vm.editOrderCtrl = editOrderCtrl;

    function newResourceTypeCtrl($mdDialog) {
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
    }

    function editResourceTypeCtrl($mdDialog, resourceType) {
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
    }

    function newResourceCtrl($mdDialog, resourceTypes) {
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
    }

    function editResourceCtrl($mdDialog, resource) {
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
    }

    function newProductCtrl($mdDialog, resourceTypes) {
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
        vm.findResource = findResource;
        vm.moveUp = moveUp;
        vm.moveDown = moveDown;
        vm.removeProductOperation = removeProductOperation;

        function removeProductOperation(productOperation) {
            var index = vm.productOperations.map(function(e) {return e.$$hashKey}).indexOf(productOperation.$$hashKey);
            if(index >= 0) {
                vm.productOperations.splice(index, 1);
            }
        }

        function moveUp(item) {
            var index = vm.productOperations.map(function(e) {return e.$$hashKey;}).indexOf(item.$$hashKey);
            if(index <= 0) {
                return;
            }
            else {
                var operation = vm.productOperations[index-1];
                vm.productOperations[index-1] = vm.productOperations[index];
                vm.productOperations[index] = operation;
            }
        }

        function moveDown(item) {
            var index = vm.productOperations.map(function(e) {return e.$$hashKey;}).indexOf(item.$$hashKey);
            if(index < 0 || index == vm.productOperations.length-1) {
                return;
            }
            else {
                var operation = vm.productOperations[index+1];
                vm.productOperations[index+1] = vm.productOperations[index];
                vm.productOperations[index] = operation;
            }
        }

        function setStyle(style) {
            vm.style = style;
        }

        function addProductOperation() {
            vm.productOperations.push(new ProductOperation(vm.name, vm.description, vm.duration, vm.prepDuration, vm.resource.id));
            vm.name = '';
            vm.description = '';
            vm.duration = '00:00:00';
            vm.prepDuration = '00:00:00';
            vm.resource = {};
        }

        function answer() {
            var product = new Product(
                vm.productName,
                vm.productDescription,
                vm.productAttr1,
                vm.productAttr2,
                vm.productAttr3,
                vm.productOperations);
            $mdDialog.hide(JSON.parse(angular.toJson(product)));
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function findResource(id) {
            for (var i = 0; i < vm.resourceTypes.length; i++) {
                var index = vm.resourceTypes[i].resources.map(function(e) {return e.id;}).indexOf(id);
                if(index >= 0) {
                    return vm.resourceTypes[i].resources[index];
                }
            }
        }
    }

    function editProductCtrl($mdDialog, resourceTypes, product) {
        var vm = this;
        vm.resourceTypes = resourceTypes;
        vm.product = {};
        angular.copy(product, vm.product);
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
        vm.findResource = findResource;
        vm.moveUp = moveUp;
        vm.moveDown = moveDown;

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
            vm.product.productOperations.push(new ProductOperation(vm.name, vm.description, vm.duration, vm.prepDuration, vm.resource.id));
            vm.name = '';
            vm.description = '';
            vm.duration = '00:00:00';
            vm.prepDuration = '00:00:00';
            vm.resource = {};
        }

        function findResource(id) {
            for (var i = 0; i < vm.resourceTypes.length; i++) {
                var index = vm.resourceTypes[i].resources.map(function(e) {return e.id;}).indexOf(id);
                if(index >= 0) {
                    return vm.resourceTypes[i].resources[index];
                }
            }
        }

        function moveUp(item) {
            var index = vm.product.productOperations.map(function(e) {return e.$$hashKey;}).indexOf(item.$$hashKey);
            if(index <= 0) {
                return;
            }
            else {
                var operation = vm.product.productOperations[index-1];
                vm.product.productOperations[index-1] = vm.product.productOperations[index];
                vm.product.productOperations[index] = operation;
            }
        }

        function moveDown(item) {
            var index = vm.product.productOperations.map(function(e) {return e.$$hashKey;}).indexOf(item.$$hashKey);
            if(index < 0 || index == vm.product.productOperations.length-1) {
                return;
            }
            else {
                var operation = vm.product.productOperations[index+1];
                vm.product.productOperations[index+1] = vm.product.productOperations[index];
                vm.product.productOperations[index] = operation;
            }
        }

        function answer() {
            var product = new Product(
                vm.product.name,
                vm.product.description,
                vm.product.attribute1,
                vm.product.attribute3,
                vm.product.attribute3,
                vm.product.productOperations)
            product.setId(vm.product.id);
            product.setCreatedOn(vm.product.createdOn);
            product.setEditedOn(vm.product.editedOn);
            $mdDialog.hide(JSON.parse(angular.toJson(product)));
        }

        function cancel() {
            $mdDialog.cancel();
        }
    }

    function newOrderCtrl($mdDialog, products) {
        var vm = this;
        vm.products = products;
        vm.name = '';
        vm.description = '';
        vm.dueDate = '';
        vm.dueTime = '';
        vm.orderProducts = [];
        vm.answer = answer;
        vm.cancel = cancel;


        function answer() {
            var dueDate = new Date();
            dueDate.setYear(vm.dueDate.getFullYear());
            dueDate.setMonth(vm.dueDate.getMonth());
            dueDate.setDate(vm.dueDate.getDate());
            dueDate.setHours(vm.dueTime.getHours() + vm.dueDate.getTimezoneOffset() / (-60));
            dueDate.setMinutes(vm.dueTime.getMinutes());
            dueDate.setSeconds(vm.dueTime.getSeconds());
            dueDate = dueDate.toISOString().slice(0, 19);
            var order = new Order(
                vm.name,
                vm.description,
                dueDate,
                vm.orderProducts);
            $mdDialog.hide(JSON.parse(angular.toJson(order)));
        }

        function cancel() {
            $mdDialog.cancel();
        }
    }

    function editOrderCtrl($mdDialog, order) {
        var vm = this;
        vm.order = {};
        angular.copy(order, vm.order);
        vm.answer = answer;
        vm.cancel = cancel;

        function answer() {
            var order = new Order(
                vm.order.name,
                vm.order.description,
                vm.order.dueDate,
                vm.order.orderProducts);
            order.setId(vm.order.id);
            order.setCreatedOn(vm.order.createdOn);
            order.setEditedOn(vm.order.editedOn);
            $mdDialog.hide(order);
        }

        function cancel() {
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

    function showErrorToast(errorCode) {
        var toast = $mdToast.simple()
            .textContent("HTTP " + errorCode + " error occured! Your changes have not been saved")
            .position('top right')
            .hideDelay(5000)
            .theme('md-warn');
        $mdToast.show(toast);
    }
}