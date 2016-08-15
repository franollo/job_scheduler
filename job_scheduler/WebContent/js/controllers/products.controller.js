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
    vm.extendProduct = extendProduct;

    vm.products = [];
    vm.selectedOrders = [];
    vm.extendedProductId = 0;

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
        return vm.selectedOrders.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedOrders.length === vm.products.length) {
            vm.selectedOrders = [];
        } else if (vm.selectedOrders.length === 0 || vm.selectedOrders.length > 0) {
            vm.selectedOrders = vm.products.slice(0);
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
        return vm.selectedOrders.length === vm.products.length
    }

    function isIndeterminate() {
        return (vm.selectedOrders.length !== 0 && vm.selectedOrders.length !== vm.products.length );
    }

    function editProduct(product) {
        openDialogEditProduct(mainController.resourceTypes, product);
    }

    function removeProduct(product) {
        console.log("REMOVE ONE");
    }

    function removeProducts() {
        console.log("REMOVE MANY");
    }

    function newProduct() {
        openDialogNewProduct(mainController.resourceTypes);
    }

    function addProduct(data) {
        console.log(data);
        vm.products.push(data);
    }

    function extendProduct(id) {
        var index = vm.products.map(function(e) {return e.id;}).indexOf(id);
        if(vm.products[index].productOperations.length > 0) {
            if(vm.extendedProductId != id) {
                vm.extendedProductId = id;
            }
            else {
                vm.extendedProductId = 0;
            }
        }
    }

    function openDialogNewProduct(resourceTypes) {
        $mdDialog.show({
            locals: {resourceTypes: resourceTypes},
            controller: dialogsService.newProductCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/new_product.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            console.log(answer);
            dataService.saveProduct(answer)
                .then(addProduct)
                .catch(fireError);
        });
    }

    function openDialogEditProduct(resourceTypes, product) {
        $mdDialog.show({
            locals: {resourceTypes: resourceTypes, product: product},
            controller: dialogsService.editProductCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/edit_product.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            // console.log(answer);
            // dataService.saveProduct(answer)
            //     .then(addProduct)
            //     .catch(fireError);
        });
    }
}
