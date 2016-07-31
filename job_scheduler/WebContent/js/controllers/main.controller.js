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
    vm.getProducts = getProducts;
    vm.getResources = getResources;
    vm.getOrders = getOrders;
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
    vm.dialogEditOrder = dialogEditOrder;
    vm.dialogOpenProductionPlan = dialogOpenProductionPlan;
    vm.resources = [];
    vm.products = [];
    vm.orders = [];
    vm.productionPlans = [];
    vm.productionPlan = {};
    vm.user = {};
    vm.selectedIndex = 1;
    vm.showToolbar = true;

    timelineService.init("js-timeline");

    /**
     * DATA
     */

    function putProducts(data) {
        vm.resources = data;
    }

    function putResources(data) {
        vm.products = data;
    }

    function putOrders(data) {
        vm.orders = data;
    }

    function putProductionPlans(data) {
        vm.productionPlans = data;
    }

    function putProductionPlan(data) {
        vm.productionPlan = data;
    }

    function putUser(data) {
        vm.user = data;
    }
    
    function getProducts() {
        dataService.getProducts()
            .then(putProducts)
            .catch(fireError);
    }

    function getResources() {
        dataService.getResources()
            .then(putResources)
            .catch(fireError);
    }

    function getOrders() {
        dataService.getOrders()
            .then(putOrders)
            .catch(fireError);
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
     * UTILS
     */
    
    function spliceJobs(job) {
        vm.jobs.splice(vm.jobs.indexOf(job), 1);
    }

    function spliceResources(resource) {
        vm.resources.splice(vm.resources.indexOf(resource), 1);
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
            locals: {dataToPass: vm.resources},
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

    function dialogEditOrder() {
        $mdDialog.show({
            locals: {dataToPass: vm.order},
            controller: dialogsService.editOrderController,
            controllerAs: 'ctrl',
            templateUrl: 'html/dialogs/edit_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.updateOrder(answer)
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
            // dataService.openOrder(answer)
            //     .then(saveOrder)
            //     .catch(fireError);
        });
    }

    //    vm.test = [];

    // function saveTest(data) {
    //     vm.test = data;
    //     console.log(data);
    // }

    // vm.errorTest = function () {
    //     dataService.errorTest()
    //         .then(saveTest)
    //         .catch(fireError)
    // };

}
