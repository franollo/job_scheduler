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
    vm.hide = hide;
    vm.deleteJob = deleteJob;
    vm.deleteResource = deleteResource;
    vm.updateJob = updateJob;
    vm.updateResource = updateResource;
    vm.focusTimeline = focusTimeline;
    vm.toggleSnappingTimeline = toggleSnappingTimeline;
    vm.showCurrentTime = showCurrentTime;
    vm.toggleLockTimeline = toggleLockTimeline;
    vm.dialogAddJob = dialogAddJob;
    vm.dialogAddResource = dialogAddResource;
    vm.dialogNewOrder = dialogNewOrder;
    vm.dialogAddJobsToOrder = dialogAddJobsToOrder;
    vm.dialogEditOrder = dialogEditOrder;
    vm.dialogOpenOrder = dialogOpenOrder;
    vm.logout = logout;
    vm.undo = undo;
    vm.redo = redo;
    vm.resources = [];
    vm.jobs = [];
    vm.orders = [];
    vm.user = {};
    vm.selectedIndex = 1;
    vm.isSmall = false;

    timelineService.init("js-timeline");

    function hide() {
        vm.isSmall = !vm.isSmall;
    }

    function deleteJob(job) {
        spliceJobs(job);
        dataService.deleteJob(job)
            .catch(fireError);
    }

    function deleteResource(resource) {
        spliceResources(resource);
        dataService.deleteResource(resource)
            .catch(fireError);
    }

    function updateJob(job) {
        dataService.updateJob(job)
            .catch(fireError);
    }

    function updateResource(resource) {
        dataService.updateResource(resource)
            .catch(fireError);
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

    function saveResources(data) {
        vm.resources = data;
    }

    function saveJobs(data) {
        vm.jobs = data;
    }

    function saveOrders(data) {
        vm.orders = data;
    }

    function saveUser(data) {
        vm.user = data;
    }

    function saveOrder(data) {
        items.clear();
        groups.clear();
        items.add(data.items);
        groups.add(data.groups);
        vm.order = data.order;
        timeline.fit({animation: true});
        showSimpleToast("Data loaded succesfully")
    }

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

    function getJobs() {
        dataService.getJobs()
            .then(saveJobs)
            .catch(fireError);
    }

    function getOrders() {
        dataService.getOrders()
            .then(saveOrders)
            .catch(fireError);
    }

    function getResources() {
        dataService.getResources()
            .then(saveResources)
            .catch(fireError);
    }

    function whoAmI() {
        dataService.whoAmI()
            .then(saveUser)
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

    function showSimpleToast(text) {
        //noinspection JSUnresolvedFunction
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
            templateUrl: '../dialogs/add_job.html',
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
            templateUrl: '../dialogs/new_resource.html',
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
            templateUrl: '../dialogs/new_order.html',
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
            templateUrl: '../dialogs/add_job_order.html',
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
            templateUrl: '../dialogs/edit_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.updateOrder(answer)
                .then(saveOrder)
                .catch(fireError);
        });
    }

    function dialogOpenOrder() {
        $mdDialog.show({
            controller: dialogsService.openOrderController,
            controllerAs: 'ctrl',
            templateUrl: '../dialogs/open_order.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.openOrder(answer)
                .then(saveOrder)
                .catch(fireError);
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
