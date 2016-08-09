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
    vm.resourceTypes = [];
    vm.selectedProducts = [];
    vm.idToRemove = 0;

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
        console.log("EDIT TYPE");
    }

    function editResource(order) {
        console.log("EDIT");
    }

    function removeResourceType(resourceType) {
        vm.idToRemove = resourceType.id;
        openDialogDeleteConfirm();
        console.log("REMOVE ONE TYPE");
    }

    function removeResourceTypes(order) {
        console.log("REMOVE MANY TYPES");
    }

    function removeResource() {
        console.log("REMOVE RES");
    }

    function newResourceType() {
        console.log("NEW TYPE");
        openDialogNewResType();
    }

    function newResource() {
        console.log("NEW");
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

    function openDialogDeleteConfirm() {
        var confirm = $mdDialog.confirm()
            .title('Do you want to delete this resource type?')
            .ok('yes')
            .cancel('cancel');
        $mdDialog.show(confirm).then(function() {
            removeHelper();
        });
    };

    function removeHelper() {
        dataService.removeResourceType(vm.idToRemove)
            .then(cutResourceType)
            .catch(fireError);
    }

    function addResourceType(data) {
        console.log(data);
        vm.resourceTypes.push(data);
    }

    function updateResourceType(data) {
        var index = vm.resourceTypes.map(function(e) {return e.id;}).indexOf(data.id);
        vm.resourceTypes[index] = data;
        console.log(data);
    }

    function cutResourceType(data) {
        var index = vm.resourceTypes.map(function(e) {return e.id;}).indexOf(data);
        vm.resourceTypes.splice(index, 1);
    }
}
