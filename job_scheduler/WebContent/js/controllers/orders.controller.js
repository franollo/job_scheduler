angular
    .module('app')
    .controller('ordersController', ordersController);

ordersController.$inject = ['$document',
                            '$mdDialog',
                            'dataService',
                            'dialogsService'];

function ordersController($document,
                          $mdDialog,
                          dataService,
                          dialogsService) {
    var vm = this;
    var document = $document[0];
    vm.extendOrder = extendOrder;
    vm.toggleAll = toggleAll;
    vm.toggle = toggle;
    vm.exists = exists;
    vm.isChecked = isChecked;
    vm.isIndeterminate = isIndeterminate;
    vm.editOrder = editOrder;
    vm.removeOrder = removeOrder;
    vm.removeOrders = removeOrders;
    vm.newOrder = newOrder;
    vm.extendOrder = extendOrder;
    vm.extendedOrderId = 0;
    vm.orders = [];
    vm.idToRemove = 0;
    vm.selectedOrders = [];

    dataService.getOrders()
        .then(putOrders)
        .catch(fireError);

    function fireError(error) {
        dialogsService.showErrorToast(error.status);
    }

    function putOrders(data) {
        vm.orders = data;
        mainController.orders = vm.orders;
    }

    function extendOrder(id) {
        if(vm.extendedOrderId != id) {
            vm.extendedOrderId = id;
        }
        else {
            vm.extendedOrderId = 0;
        }
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

    function editOrder(order) {
        openDialogEditOrder(order);
    }

    function removeOrder(order) {
        vm.idToRemove = order.id;
        openDialogDeleteOrder();
    }

    function removeOrders() {
        openDialogDeleteMultProduct();
    }

    function newOrder() {
        openDialogNewOrder();
    }

    function openDialogEditOrder(order) {
        $mdDialog.show({
            locals: {order: order},
            controller: dialogsService.editOrderCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/edit_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {

        });
    }

    function openDialogNewOrder() {
        $mdDialog.show({
            locals: {products: mainController.products},
            controller: dialogsService.newOrderCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/new_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            console.log(answer);
            dataService.saveOrder(answer)
                .then(putOrder)
                .catch(fireError);
        });
    }

    function openDialogDeleteOrder() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete this order?')
            .textContent('It can affect your production plans')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelper();
        });
    }

    function openDialogDeleteMultProduct() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete selected orders?')
            .textContent('It can affect your production plans')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelperMult();
        });
    }

    function extendOrder(id) {
        var index = vm.orders.map(function(e) {return e.id;}).indexOf(id);
        if(vm.orders[index].products.length > 0) {
            if(vm.extendedOrderId != id) {
                vm.extendedOrderId = id;
            }
            else {
                vm.extendedOrderId = 0;
            }
        }
    }

    function putOrder(order) {
        vm.orders.push(order);
    }


    function cutOrder(data) {
        var index = vm.orders.map(function(e) {return e.id;}).indexOf(data);
        if(index >= 0) {
            vm.orders.splice(index, 1);
            vm.idToRemove = 0;
        }
    }

    function cutOrders(data) {
        for(var i = 0; i < data.length; i++) {
            var index = vm.orders.map(function(e) {return e.id;}).indexOf(data[i]);
            if(index >= 0) {
                vm.orders.splice(index, 1);
                vm.selectedOrders = [];
            }
        }
    }

    function removeHelper() {
        dataService.removeOrder(vm.idToRemove)
            .then(cutOrder)
            .catch(fireError);
    }

    function removeHelperMult() {
        var idsToRemove = [];
        for(var i = 0; i < vm.selectedOrders.length; i++) {
            idsToRemove.push(vm.selectedOrders[i].id)
        }
        dataService.removeOrders(idsToRemove)
            .then(cutOrders)
            .catch(fireError);
    }
}
