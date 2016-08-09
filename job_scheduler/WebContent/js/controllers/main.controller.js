angular
    .module('app')
    .controller('mainController', mainController);

mainController.$inject = ['$document',
    '$mdDialog',
    '$mdToast',
    'authService',
    'dataService',
    'bufferService',
    'dialogsService',
    'timelineService'];

function mainController($document,
                        $mdDialog,
                        $mdToast,
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
    vm.showCurrentTime = showCurrentTime;
    vm.toggleSnappingTimeline = toggleSnappingTimeline;
    vm.focusTimeline = focusTimeline;
    vm.spliceJobs = spliceJobs;
    vm.spliceResources = spliceResources;
    vm.fireError = fireError;
    vm.undo = undo;
    vm.redo = redo;
    vm.showSimpleToast = showSimpleToast;
    vm.dialogAddJob = dialogAddJob;
    vm.dialogAddResource = dialogAddResource;
    vm.dialogNewOrder = dialogNewOrder;
    vm.dialogAddJobsToOrder = dialogAddJobsToOrder;
    vm.dialogOpenProductionPlan = dialogOpenProductionPlan;
    vm.extendOrder = extendOrder;
    vm.openOrdersWorkspace = openOrdersWorkspace;
    vm.openProductsWorkspace = openProductsWorkspace;
    vm.openResourcesWorkspace = openResourcesWorkspace;
    vm.closeWorkspace = closeWorkspace;
    vm.extendedOrderId = 0;
    vm.resourceTypes = [];
    vm.products = [];
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
    dataService.getProductionPlan(1)
        .then(putProductionPlan)
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
        authService.logout().then(function () {
            vm.log = "";
            vm.nam = "";
            vm.sur = "";
            vm.error = "";
            vm.authenticated = authService.authenticated;
        });
    }

    /**
     * TIMELINE
     */
    
    function toggleToolbar() {
        vm.showToolbar = !vm.showToolbar;
    }

    function toggleLockTimeline() {
        vm.timelineLocked = !vm.timelineLocked;
        if (vm.timelineLocked = false) {
            timelineService.unlock();
        }
        else {
            timelineService.lock();
        }
    }

    function showCurrentTime() {
        vm.timelineCurrentTime = !vm.timelineCurrentTime;
        if (vm.timelineCurrentTime = false) {
            timelineService.hideCurrentTime();
        }
        else {
            timelineService.showCurrentTime();
        }
    }

    function toggleSnappingTimeline() {
        vm.snapping = !vm.snapping;
        if (snapping == false) {
            timelineService.disableSnapping();
        }
        else {
            timelineService.enableSnapping();
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

    function fireError(error) {
        console.error("AN ERROR OCCURED");
        console.log(error);
    }

    function undo() {
        bufferService.undo(timelineService.getItems());
    }

    function redo() {
        bufferService.redo(timelineService.getItems());
    }
    
    
    /**
     * DIALOGS AND TOASTS
     */

    function showSimpleToast(text) {
        $mdToast.show(
            $mdToast.simple()
                .textContent(text)
                .position('top right')
                .hideDelay(3000)
                .parent(angular.element(document.body))
        );
    }

    function dialogAddJob() {
        $mdDialog.show({
            locals: {dataToPass: vm.resourceTypes},
            controller: dialogsService.DialogController1,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/add_job.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.newJob(answer)
                .then(getJobs)
                .catch(fireError);
        });
    }

    function dialogAddResource() {
        $mdDialog.show({
            controller: dialogsService.DialogController4,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/new_resource.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.newResource(answer)
                .then(getResources)
                .catch(fireError);
        });
    }

    function dialogNewOrder() {
        $mdDialog.show({
            locals: {dataToPass: vm.jobs},
            controller: dialogsService.newOrderController,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/new_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.newOrder(answer)
                .then(saveOrder)
                .catch(fireError);
        });
    }

    function dialogAddJobsToOrder() {
        $mdDialog.show({
            locals: {dataToPass: vm.jobs},
            controller: dialogsService.addJobOrderController,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/add_job_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.addJobOrder(answer[0])
                .then(saveOrder)
                .catch(fireError);
        });
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
                .then(putProductionPlan)
                .catch(fireError);
        });
    }
}
