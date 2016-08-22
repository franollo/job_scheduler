angular
    .module('app')
    .service('dialogsService', dialogsService);

function dialogsService(dataService, $mdToast) {
    var vm = this;
    vm.openProductionPlanCtrl = openProductionPlanCtrl;
    vm.newProductionPlanCtrl = newProductionPlanCtrl;
    vm.editProductionPlanCtrl = editProductionPlanCtrl;
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
        vm.addOrderProduct = addOrderProduct;
        vm.removeOrderProduct = removeOrderProduct;

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

        function addOrderProduct(product, amount) {
            vm.orderProducts.push(new OrderProduct(product, amount));
        }

        function removeOrderProduct(orderProduct) {
            var index = vm.orderProducts.map(function(e) {return e.$$hashKey;}).indexOf(orderProduct.$$hashKey);
            if(index >= 0) {
                vm.orderProducts.splice(index, 1);
            }
        }
    }

    function editOrderCtrl($mdDialog, order, products) {
        var vm = this;
        vm.products = products;
        vm.order = {};
        angular.copy(order, vm.order);
        vm.dueDate = new Date(new Date(vm.order.dueDate).valueOf() +new Date(vm.order.dueDate).getTimezoneOffset() * 60000);
        vm.dueTime = new Date(new Date(vm.order.dueDate).valueOf() +new Date(vm.order.dueDate).getTimezoneOffset() * 60000);
        vm.answer = answer;
        vm.cancel = cancel;
        vm.addOrderProduct = addOrderProduct;
        vm.removeOrderProduct = removeOrderProduct;

        function answer() {
            var dueDate = new Date();
            dueDate.setYear(vm.dueDate.getFullYear());
            dueDate.setMonth(vm.dueDate.getMonth());
            dueDate.setDate(vm.dueDate.getDate());
            dueDate.setHours(vm.dueTime.getHours());
            dueDate.setMinutes(vm.dueTime.getMinutes());
            dueDate.setSeconds(vm.dueTime.getSeconds());
            dueDate = dueDate.toISOString().slice(0, 19);
            var order = new Order(
                vm.order.name,
                vm.order.description,
                dueDate,
                vm.order.orderProducts);
            order.setId(vm.order.id);
            order.setCreatedOn(vm.order.createdOn);
            order.setEditedOn(vm.order.editedOn);
            $mdDialog.hide(JSON.parse(angular.toJson(order)));
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function addOrderProduct(product, amount) {
            vm.order.orderProducts.push(new OrderProduct(product, amount));
        }

        function removeOrderProduct(orderProduct) {
            var index = vm.order.orderProducts.map(function(e) {return e.$$hashKey;}).indexOf(orderProduct.$$hashKey);
            if(index >= 0) {
                vm.order.orderProducts.splice(index, 1);
            }
        }
    }

    function openProductionPlanCtrl($scope, $mdDialog) {
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

    function newProductionPlanCtrl($mdDialog, orders) {
        var vm = this;
        vm.orders = orders;
        vm.selectedOrders = [];
        vm.name = '';
        vm.startDate = '';
        vm.answer = answer;
        vm.cancel = cancel;
        vm.select = select;
        vm.exists = exists;
        vm.toggleAll = toggleAll;
        vm.toggle = toggle;
        vm.isChecked = isChecked;
        vm.isIndeterminate = isIndeterminate;
        vm.productionPlans = [];
        vm.selectedPlanId = 0;

        function answer() {
            var productionPlan = new ProductionPlan(vm.name, vm.startDate, vm.selectedOrders);
            $mdDialog.hide(JSON.parse(angular.toJson(productionPlan)));
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function select(id) {
            vm.selectedPlanId = id;
        }

        function exists(item) {
            return vm.selectedOrders.indexOf(item) > -1;
        }

        function toggleAll() {
            if(vm.selectedOrders.length === vm.orders.length) {
                vm.selectedOrders = [];
            } else if (vm.selectedOrders.length === 0 || vm.selectedOrders.length > 0) {
                vm.selectedOrders = vm.orders.slice(0);
            }
        }

        function toggle(item) {
            var idx = vm.selectedOrders.indexOf(item);
            if(idx > -1) {
                vm.selectedOrders.splice(idx, 1);
            }
            else {
                vm.selectedOrders.push(item);
            }
        }

        function isChecked() {
            return vm.selectedOrders.length === vm.orders.length
        }

        function isIndeterminate() {
            return (vm.selectedOrders.length !== 0 && vm.selectedOrders.length !== vm.orders.length );
        }
    }

    function editProductionPlanCtrl($mdDialog, orders, productionPlan) {
        var vm = this;
        vm.orders = orders;
        vm.selectedOrders = productionPlan.orders;
        vm.name = productionPlan.name;
        vm.startDate = productionPlan.start;
        vm.answer = answer;
        vm.cancel = cancel;
        vm.exists = exists;
        vm.toggleAll = toggleAll;
        vm.toggle = toggle;
        vm.isChecked = isChecked;
        vm.isIndeterminate = isIndeterminate;
        vm.selectedPlanId = 0;

        function answer() {
            var productionPlanSend = new ProductionPlan(vm.name, vm.startDate, vm.selectedOrders);
            productionPlanSend.setId(productionPlan.id);
            productionPlanSend.setCreatedOn(productionPlan.createdOn);
            productionPlanSend.setEditedOn(productionPlan.editedOn);
            $mdDialog.hide(JSON.parse(angular.toJson(productionPlanSend)));
        }

        function cancel() {
            $mdDialog.cancel();
        }

        function exists(item) {
            return vm.selectedOrders.map(function(e){return e.id;}).indexOf(item.id) > -1;
        }

        function toggleAll() {
            if(vm.selectedOrders.length === vm.orders.length) {
                vm.selectedOrders = [];
            } else if (vm.selectedOrders.length === 0 || vm.selectedOrders.length > 0) {
                vm.selectedOrders = vm.orders.slice(0);
            }
        }

        function toggle(item) {
            var idx = vm.selectedOrders.map(function(e){return e.id;}).indexOf(item.id);
            if(idx > -1) {
                vm.selectedOrders.splice(idx, 1);
            }
            else {
                vm.selectedOrders.push(item);
            }
            console.log(vm.selectedOrders);
        }

        function isChecked() {
            return vm.selectedOrders.length === vm.orders.length
        }

        function isIndeterminate() {
            return (vm.selectedOrders.length !== 0 && vm.selectedOrders.length !== vm.orders.length );
        }
    }

    function showErrorToast(error) {
        var message = '';
        if(error.status == undefined) {
            message = error.toString();
        }
        else {
            message = "HTTP " + error.status + " error occured! Operation failed."
        }
        var toast = $mdToast.simple()
            .textContent(message)
            .position('top right')
            .hideDelay(5000)
            .theme('md-warn');
        $mdToast.show(toast);
    }
}