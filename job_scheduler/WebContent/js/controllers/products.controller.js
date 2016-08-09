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
    vm.selectedProducts = [];

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
        return vm.selectedProducts.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedProducts.length === vm.products.length) {
            vm.selectedProducts = [];
        } else if (vm.selectedProducts.length === 0 || vm.selectedProducts.length > 0) {
            vm.selectedProducts = vm.products.slice(0);
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
        return vm.selectedProducts.length === vm.products.length
    }

    function isIndeterminate() {
        return (vm.selectedProducts.length !== 0 && vm.selectedProducts.length !== vm.products.length );
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
