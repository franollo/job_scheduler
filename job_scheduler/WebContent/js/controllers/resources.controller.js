angular
    .module('app')
    .controller('resourcesController', resourcesController);

resourcesController.$inject = [
    '$mdDialog',
    'dataService',
    'dialogsService'];

function resourcesController($mdDialog,
                             dataService,
                             dialogsService) {
    var vm = this;
    vm.toggleAll = toggleAll;
    vm.toggle = toggle;
    vm.exists = exists;
    vm.isChecked = isChecked;
    vm.isIndeterminate = isIndeterminate;
    vm.resources = [];
    vm.selectedProducts = [];

    dataService.getResources()
        .then(putResources)
        .catch(fireError);

    function putResources(data) {
        vm.resources = data;
        mainController.resources = vm.resources;
    }

    function fireError(error) {
        console.error("AN ERROR OCCURED");
        console.log(error);
    }

    function exists(item) {
        return vm.selectedProducts.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedProducts.length === vm.resources.length) {
            vm.selectedProducts = [];
        } else if (vm.selectedProducts.length === 0 || vm.selectedProducts.length > 0) {
            vm.selectedProducts = vm.resources.slice(0);
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
        return vm.selectedProducts.length === vm.resources.length
    }

    function isIndeterminate() {
        return (vm.selectedProducts.length !== 0 && vm.selectedProducts.length !== vm.resources.length );
    }
}
