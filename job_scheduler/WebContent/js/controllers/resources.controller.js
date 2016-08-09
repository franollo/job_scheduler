angular
    .module('app')
    .controller('resourcesController', resourcesController);

resourcesController.$inject = [
    '$document',
    '$mdDialog',
    'dataService',
    'dialogsService'];

function resourcesController($document,
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
    vm.editResourceType = editResourceType;
    vm.editResource = editResource;
    vm.removeResourceType = removeResourceType;
    vm.removeResourceTypes = removeResourceTypes;
    vm.removeResource = removeResource;
    vm.newResourceType = newResourceType;
    vm.newResource = newResource;
    vm.extendResType = extendResType;
    vm.extendedResTypeId = 0;
    vm.resourceTypes = [];
    vm.selectedProducts = [];
    vm.idToRemoveType = 0;
    vm.idToRemoveRes = 0;

    dataService.getResources()
        .then(putResources)
        .catch(fireError);

    function putResources(data) {
        vm.resourceTypes = data;
        mainController.resourceTypes = vm.resourceTypes;
    }

    function fireError(error) {
        console.error("AN ERROR OCCURED");
        console.log(error);
    }

    function exists(item) {
        return vm.selectedProducts.indexOf(item) > -1;
    }

    function toggleAll() {
        if(vm.selectedProducts.length === vm.resourceTypes.length) {
            vm.selectedProducts = [];
        } else if (vm.selectedProducts.length === 0 || vm.selectedProducts.length > 0) {
            vm.selectedProducts = vm.resourceTypes.slice(0);
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
        return vm.selectedProducts.length === vm.resourceTypes.length
    }

    function isIndeterminate() {
        return (vm.selectedProducts.length !== 0 && vm.selectedProducts.length !== vm.resourceTypes.length );
    }

    function editResourceType(resourceType) {
        openDialogEditResType(resourceType);
    }

    function editResource(resource) {
        openDialogEditRes(resource);
    }

    function removeResourceType(resourceType) {
        vm.idToRemoveType = resourceType.id;
        openDialogDeleteTypeConfirm();
    }

    function removeResourceTypes(order) {
        console.log("REMOVE MANY TYPES");
    }

    function removeResource(resource) {
        vm.idToRemoveRes = resource.id;
        openDialogDeleteResConfirm();
    }

    function newResourceType() {
        openDialogNewResType();
    }

    function newResource() {
        openDialogNewRes();
    }

    function openDialogNewResType() {
        $mdDialog.show({
            controller: dialogsService.newResourceTypeCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/new_resource_type.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.saveResourceType(answer)
                .then(addResourceType)
                .catch(fireError);
        });
    }

    function openDialogNewRes() {
        $mdDialog.show({
            locals: {resourceTypes: vm.resourceTypes},
            controller: dialogsService.newResourceCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/new_resource.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.saveResource(answer)
                .then(addResource)
                .catch(fireError);
        });
    }

    function openDialogEditResType(resourceType) {
        $mdDialog.show({
            locals: {resourceType: resourceType},
            controller: dialogsService.editResourceTypeCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/edit_resource_type.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.saveResourceType(answer)
                .then(updateResourceType)
                .catch(fireError);
        });
    }

    function openDialogEditRes(resource) {
        $mdDialog.show({
            locals: {resource: resource},
            controller: dialogsService.editResourceCtrl,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/edit_resource.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.saveResource(answer)
                .then(updateResource)
                .catch(fireError);
        });
    }

    function openDialogDeleteTypeConfirm() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete this resource type?')
            .textContent('Related resources and products operations will be removed')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelperType();
        });
    }

    function openDialogDeleteResConfirm() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete this resource?')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelperResource();
        });
    }

    function removeHelperType() {
        dataService.removeResourceType(vm.idToRemoveType)
            .then(cutResourceType)
            .catch(fireError);
    }

    function removeHelperResource() {
        dataService.removeResource(vm.idToRemoveRes)
            .then(cutResource)
            .catch(fireError);
    }

    function addResource(data) {
        var index = vm.resourceTypes.map(function(e) {return e.id;}).indexOf(data.resourceTypeId);
        if(index > 0) {
            vm.resourceTypes[index].resources.push(data);
        }
    }

    function addResourceType(data) {
        vm.resourceTypes.push(data);
    }

    function updateResourceType(data) {
        var index = vm.resourceTypes.map(function(e) {return e.id;}).indexOf(data.id);
        if(index > 0) {
            vm.resourceTypes[index] = data;
        }
    }

    function updateResource(data) {
        console.log(data);
        console.log(vm.resourceTypes);
        var index;
        for (var i = 0; i < vm.resourceTypes.length; i++) {
            index = vm.resourceTypes[i].resources.map(function (e) { return e.id;}).indexOf(data.id);
            console.log(index);
            if (index > 0) {
                console.log(vm.resourceTypes[i].resources[index])
                vm.resourceTypes[i].resources[index] = data;
                console.log(vm.resourceTypes[i].resources[index])
                break;
            }
        }
    }

    function cutResourceType(data) {
        var index = vm.resourceTypes.map(function(e) {return e.id;}).indexOf(data);
        if(index > 0) {
            vm.resourceTypes.splice(index, 1);
        }
    }

    function cutResource(data) {
        var index;
        for (var i = 0; i < vm.resourceTypes.length; i++) {
            index = vm.resourceTypes[i].resources.map(function(e) {return e.id;}).indexOf(data);
            if(index > 0) {
                vm.resourceTypes[i].resources.splice(index, 1);
                break;
            }
        }
    }

    function extendResType(id) {
        if(vm.extendedResTypeId != id) {
            vm.extendedResTypeId = id;
        }
        else {
            vm.extendedResTypeId = 0;
        }
    }
}
