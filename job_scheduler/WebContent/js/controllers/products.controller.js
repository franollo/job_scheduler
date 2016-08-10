angular
    .module('app')
    .controller('productsController', productsController);

productsController.$inject = [
    '$mdDialog',
    'dataService',
    'dialogsService'];

function productsController($mdDialog,
                            dataService,
                            dialogsService) {
    var vm = this;
    vm.toggleAll = toggleAll;
    vm.toggle = toggle;
    vm.exists = exists;
    vm.isChecked = isChecked;
    vm.isIndeterminate = isIndeterminate;
    vm.editProduct = editProduct
    vm.removeProduct = removeProduct
    vm.removeProducts = removeProducts
    vm.newProduct = newProduct

    vm.products = [];
    vm.selectedResources = [];

    dataService.getProducts()
        .then(putProducts)
        .catch(fireError);

    function putProducts(data) {
        vm.products = data;
        mainController.products = vm.products;
    }

    function fireError(error) {
        console.error("AN ERROR OCCURED");
        console.log(error);
    }

    function exists(item) {
        return vm.selectedResources.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedResources.length === vm.products.length) {
            vm.selectedResources = [];
        } else if (vm.selectedResources.length === 0 || vm.selectedResources.length > 0) {
            vm.selectedResources = vm.products.slice(0);
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
        return vm.selectedResources.length === vm.products.length
    }

    function isIndeterminate() {
        return (vm.selectedResources.length !== 0 && vm.selectedResources.length !== vm.products.length );
    }

    function editProduct(product) {
        console.log("EDIT");
    }

    function removeProduct(product) {
        console.log("REMOVE ONE");
    }

    function removeProducts() {
        console.log("REMOVE MANY");
    }

    function newProduct() {
        console.log("NEW");
    }
}
