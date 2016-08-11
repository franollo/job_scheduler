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
    vm.selectedOrders = [];

    dataService.getOrders()
        .then(putOrders)
        .catch(fireError);

    function putOrders(data) {
        vm.orders = data;
        mainController.orders = vm.orders;
    }

    function fireError(error) {
        console.error("AN ERROR OCCURED");
        console.log(error);
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
        console.log("NEW");
    }

    function openDialogEditOrder(order) {
        $mdDialog.show({
            locals: {data: order},
            controller: dialogsService.editOrderController,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/edit_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {

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
}
