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
    vm.extendedOrderId = 0;
    vm.orders = [];
    vm.selectedProducts = [];

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
        openDialogEditOrder(order);
    }

    function removeOrder(order) {

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
