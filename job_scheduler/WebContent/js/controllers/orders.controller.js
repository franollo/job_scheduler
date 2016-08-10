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
    vm.selectedResources = [];

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
        return vm.selectedResources.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedResources.length === vm.orders.length) {
            vm.selectedResources = [];
        } else if (vm.selectedResources.length === 0 || vm.selectedResources.length > 0) {
            vm.selectedResources = vm.orders.slice(0);
        }
    }
    
    function toggle(item) {
        var idx = vm.selectedResources.indexOf(item);
        if(idx > -1) {
            vm.selectedResources.splice(idx, 1);
        }
        else {
            vm.selectedResources.push(item);
        }
    }

    function isChecked() {
        return vm.selectedResources.length === vm.orders.length
    }

    function isIndeterminate() {
        return (vm.selectedResources.length !== 0 && vm.selectedResources.length !== vm.orders.length );
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
}
