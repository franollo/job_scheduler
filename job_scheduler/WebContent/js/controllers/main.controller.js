angular
    .module('app')
    .controller('mainController', mainController);

mainController.$inject = ['$document',
    '$location',
    '$mdDialog',
    'authService',
    'dataService',
    'bufferService',
    'dialogsService',
    'timelineService'];

function mainController($document,
                        $location,
                        $mdDialog,
                        authService,
                        dataService,
                        bufferService,
                        dialogsService,
                        timelineService) {
    var vm = this;
    var document = $document[0];
    vm.snapping = true;
    vm.timelineLocked = false;
    vm.timelineCurrentTime = true;
    vm.getProductionPlans = getProductionPlans;
    vm.getProductionPlan = getProductionPlan;
    vm.getUserInfo = getUserInfo;
    vm.logout = logout;
    vm.toggleToolbar = toggleToolbar;
    vm.toggleLockTimeline = toggleLockTimeline;
    vm.toogleZoomKey = toogleZoomKey;
    vm.showCurrentTime = showCurrentTime;
    vm.toggleSnappingTimeline = toggleSnappingTimeline;
    vm.focusTimeline = focusTimeline;
    vm.spliceJobs = spliceJobs;
    vm.spliceResources = spliceResources;
    vm.undo = undo;
    vm.redo = redo;
    vm.dialogOpenProductionPlan = dialogOpenProductionPlan;
    vm.dialogNewProductionPlan = dialogNewProductionPlan;
    vm.extendOrder = extendOrder;
    vm.openOrdersWorkspace = openOrdersWorkspace;
    vm.openProductsWorkspace = openProductsWorkspace;
    vm.openResourcesWorkspace = openResourcesWorkspace;
    vm.closeWorkspace = closeWorkspace;
    vm.test = test;
    vm.extendedOrderId = 0;
    vm.orders = [];
    vm.productionPlans = [];
    vm.productionPlan = {};
    vm.user = {};
    vm.selectedIndex = 1;
    vm.showToolbar = true;
    vm.showWorkspace = {
        'productionPlan' : true,
        'orders' : false,
        'products' : false,
        'resourceTypes' : false
    };

    timelineService.init("js-timeline");
    dataService.getProductionPlan(5)
        .then(productionPlanToVIS)
        .catch(fireError);
    
    /**
     * WORKSPACE TOGGLE
     */

    function openOrdersWorkspace() {
        setFalseWorkspace();
        vm.showWorkspace.orders = true;
    }

    function openProductsWorkspace() {
        setFalseWorkspace();
        vm.showWorkspace.products = true;
    }

    function openResourcesWorkspace() {
        setFalseWorkspace();
        vm.showWorkspace.resourceTypes = true;
    }

    function setFalseWorkspace() {
        vm.showWorkspace.productionPlan = false;
        vm.showWorkspace.orders = false;
        vm.showWorkspace.products = false;
        vm.showWorkspace.resourceTypes = false;
    }

    function closeWorkspace() {
        vm.showWorkspace.productionPlan = true;
        vm.showWorkspace.orders = false;
        vm.showWorkspace.products = false;
        vm.showWorkspace.resourceTypes = false;
    }


    /**
     * DATA
     */

    function putProductionPlans(data) {
        vm.productionPlans = data;
    }

    function putProductionPlan(data) {
        vm.productionPlan = data;
    }

    function putUser(data) {
        vm.user = data;
    }

    function handleLogout(data) {
        $location.path('/login');
    }

    function getProductionPlans() {
        dataService.getProductionPlans()
            .then(putProductionPlans)
            .catch(fireError);
    }

    function getProductionPlan(productionPlanId) {
        dataService.getProductionPlan(productionPlanId)
            .then(putProductionPlan)
            .catch(fireError);
    }
    
    function getUserInfo() {
        dataService.getUserInfo()
            .then(putUser)
            .catch(fireError);
    }

    function logout() {
        authService.logout()
            .then(handleLogout)
            .catch(fireError);
    }

    /**
     * TIMELINE
     */
    
    function toggleToolbar() {
        vm.showToolbar = !vm.showToolbar;
    }

    function toggleLockTimeline() {
        vm.timelineLocked = !vm.timelineLocked;
        if (vm.timelineLocked == false) {
            timelineService.unlock();
        }
        else {
            timelineService.lock();
        }
    }

    function showCurrentTime() {
        vm.timelineCurrentTime = !vm.timelineCurrentTime;
        if (vm.timelineCurrentTime == false) {
            timelineService.hideCurrentTime();
        }
        else {
            timelineService.showCurrentTime();
        }
    }

    function toggleSnappingTimeline() {
        vm.snapping = !vm.snapping;
        if (vm.snapping == false) {
            timelineService.disableSnapping();
        }
        else {
            timelineService.enableSnapping();
        }
    }

    function toogleZoomKey() {
        vm.zoomKey = !vm.zoomKey;
        if(vm.zoomKey == true) {
            timelineService.zoomableKeyTrue();
        }
        else {
            timelineService.zoomableKeyFalse();
        }
    }

    function focusTimeline() {
        timelineService.focus();
    }

    /**
     * Orders tab
     */

    function extendOrder(id) {
        if(vm.extendedOrderId != id) {
            vm.extendedOrderId = id;
        }
        else {
            vm.extendedOrderId = 0;
        }
    }

    /**
     * UTILS
     */

    function spliceJobs(job) {
        vm.jobs.splice(vm.jobs.indexOf(job), 1);
    }

    function spliceResources(resource) {
        vm.resourceTypes.splice(vm.resourceTypes.indexOf(resource), 1);
    }

    function undo() {
        bufferService.undo(timelineService.getItems());
    }

    function redo() {
        bufferService.redo(timelineService.getItems());
    }

    function findResource(id) {
        var resourceTypes = dataService.getResourceTypes();
        for (var i = 0; i < resourceTypes.length; i++) {
            var index = resourceTypes[i].resources.map(function(e) {return e.id;}).indexOf(id);
            if(index >= 0) {
                return resourceTypes[i].resources[index];
            }
        }
    }

    function findProduct(id) {
        var products = dataService.getServiceProducts();
        var index = products.map(function(e) {return e.id;}).indexOf(id);
        if(index >= 0) {
            return products[index];
        }
    }

    function productionPlanToVIS(data) {
        vm.productionPlan = data;
        timelineService.clean();
        var groups = [];
        for(var i = 0; i < data.items.length; i++) {
            if(groups.map(function(e) {return e;}).indexOf(data.items[i].resourceId) < 0) {
                groups.push(data.items[i].resourceId);
            }
            data.items[i].group = data.items[i].resourceId;
            data.items[i].content = findProduct(data.items[i].productId).name;
            data.items[i].start = new Date(data.items[i].start);
            data.items[i].end = new Date(data.items[i].end);
        }
        for(var j = 0; j < groups.length; j++) {
            var resource = findResource(groups[j]);
            var group = {id: groups[j], content: resource.name, title: resource.description}
            timelineService.addGroups(group);
        }
        timelineService.addItems(data.items);
        timelineService.focus();
    }
    
    /**
     * DIALOGS AND TOASTS
     */

    function fireError(error){
        console.error(error);
        dialogsService.showErrorToast(error);
    }

    function dialogOpenProductionPlan() {
        $mdDialog.show({
            controller: dialogsService.openProductionPlanController,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/open_production_plan.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.getProductionPlan(answer)
                .then(productionPlanToVIS)
                .catch(fireError);
        });
    }

    function dialogNewProductionPlan() {
        dataService.getOrders()
            .then(function(data) {
                $mdDialog.show({
                    locals: {orders: data},
                    controller: dialogsService.newProductionPlanController,
                    controllerAs: 'ctrl',
                    templateUrl: 'html/dialogs/new_production_plan.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: true
                }).then(function (answer) {
                    dataService.saveProductionPlan(answer)
                        .then(productionPlanToVIS)
                        .catch(fireError);
                });
            })
            .catch(fireError);
    }
}
