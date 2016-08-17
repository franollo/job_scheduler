angular
    .module('app')
    .controller('productsController', productsController);

productsController.$inject = [
    '$document',
    '$mdDialog',
    'dataService',
    'dialogsService'];

function productsController($document,
                            $mdDialog,
                            dataService,
                            dialogsService) {
    var vm = this;
    var document = $document[0];
    vm.toggleAll = toggleAll;
    vm.toggle = toggle;
    vm.exists = exists;
    vm.isChecked = isChecked;
    vm.isIndeterminate = isIndeterminate;
    vm.editProduct = editProduct;
    vm.removeProduct = removeProduct;
    vm.removeProducts = removeProducts;
    vm.newProduct = newProduct;
    vm.extendProduct = extendProduct;
    vm.fireError = fireError;
    vm.findResource = findResource;
    vm.idToRemove = 0;

    vm.products = [];
    vm.selectedProducts = [];
    vm.extendedProductId = 0;

    dataService.getProducts()
        .then(putProducts)
        .catch(fireError);

    function putProducts(data) {
        vm.products = data;
        mainController.products = vm.products;
    }

    function fireError(error) {
        dialogsService.showErrorToast(error.status);
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
        openDialogEditProduct(mainController.resourceTypes, product);
    }

    function removeProduct(product) {
        vm.idToRemove = product.id;
        openDialogDeleteProduct()
    }

    function removeProducts() {
        openDialogDeleteMultProduct();
    }

    function newProduct() {
        openDialogNewProduct(mainController.resourceTypes);
    }

    function addProduct(data) {
        console.log(data);
        vm.products.push(data);
    }

    function replaceProdut(data) {
        var index = vm.products.map(function(e) {return e.id;}).indexOf(data.id);
        if(index >= 0) {
            vm.products[index] = data;
        }
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
            dataService.saveProduct(answer)
                .then(replaceProdut)
                .catch(fireError);
        });
    }

    function openDialogDeleteProduct() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete this product?')
            .textContent('Related product operations operations will be removed')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelper();
        });
    }

    function openDialogDeleteMultProduct() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete selected products?')
            .textContent('Related products operations operations will be removed')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelperMult();
        });
    }

    function removeHelper() {
        dataService.removeProduct(vm.idToRemove)
            .then(cutProduct)
            .catch(fireError);
    }

    function removeHelperMult() {
        var idsToRemove = [];
        for(var i = 0; i < vm.selectedProducts.length; i++) {
            idsToRemove.push(vm.selectedProducts[i].id)
        }
        dataService.removeProducts(idsToRemove)
            .then(cutProducts)
            .catch(fireError);
    }

    function cutProduct(data) {
        var index = vm.products.map(function(e) {return e.id;}).indexOf(data);
        if(index >= 0) {
            vm.products.splice(index, 1);
            vm.idToRemove = 0;
        }
    }

    function cutProducts(data) {
        for(var i = 0; i < data.length; i++) {
            var index = vm.products.map(function(e) {return e.id;}).indexOf(data[i]);
            if(index >= 0) {
                vm.products.splice(index, 1);
                vm.selectedProducts = [];
            }
        }
    }

    function findResource(id) {
        for (var i = 0; i < mainController.resourceTypes.length; i++) {
            var index = mainController.resourceTypes[i].resources.map(function(e) {return e.id;}).indexOf(id);
            if(index >= 0) {
                return mainController.resourceTypes[i].resources[index];
            }
        }
    }
}