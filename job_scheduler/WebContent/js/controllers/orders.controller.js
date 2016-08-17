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
    vm.extendedOrderId = 0;
    vm.orders = [];
    vm.selectedProducts = [];

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
        return vm.selectedProducts.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedProducts.length === vm.orders.length) {
            vm.selectedProducts = [];
        } else if (vm.selectedProducts.length === 0 || vm.selectedProducts.length > 0) {
            vm.selectedProducts = vm.orders.slice(0);
        }
    }
    
    function toggle(item) {
        var idx = vm.selectedProducts.indexOf(item);
        if(idx > -1) {
            vm.selectedProducts.splice(idx, 1);
        }
        else {
            vm.selectedProducts.push(item);
        }
    }

    function isChecked() {
        return vm.selectedProducts.length === vm.orders.length
    }

    function isIndeterminate() {
        return (vm.selectedProducts.length !== 0 && vm.selectedProducts.length !== vm.orders.length );
    }

    function editOrder(order) {
        console.log("EDIT");
        openDialogEditOrder(order);
    }

    function removeOrder(order) {
        console.log("REMOVE ONE");
    }

    function removeOrders() {
        console.log("REMOVE MANY");
    }

    function newOrder() {
        openDialogNewOrder();
        console.log("NEW");
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
}
