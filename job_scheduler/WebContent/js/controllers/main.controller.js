angular
    .module('app')
    .controller('mainController', mainController);

mainController.$inject = ['$scope', '$document', '$mdDialog', '$mdToast', 'authService', 'dataService', 'bufferService', 'dialogsService'];

function mainController($scope, $document, $mdDialog, $mdToast, authService, dataService, bufferService, dialogsService) {
    var vm = this;
    var snapping = true;
    vm.timelineLocked = false;
    vm.timelineCurrentTime = true;
    vm.hide = hide;
    vm.deleteJob = deleteJob;
    vm.deleteResource = deleteResource;
    vm.updateJob = updateJob;
    vm.updateResource = updateResource;
    vm.focusTimeline = focusTimeline;
    vm.setSnapping = setSnapping;
    vm.showCurrentTime = showCurrentTime;
    vm.lockTimeline = lockTimeline;
    vm.logout = logout;
    vm.undo = undo;
    vm.redo = redo;
    vm.resources = [];
    vm.jobs = [];
    vm.orders = [];
    vm.user = {};
    vm.selectedIndex = 1;
    vm.isSmall = true;

    dataService.getJobs()
        .then(saveJobs)
        .catch(fireError);

    dataService.getOrders()
        .then(saveOrders)
        .catch(fireError);

    dataService.getResources()
        .then(saveResources)
        .catch(fireError);

    dataService.whoAmI()
        .then(saveUser)
        .catch(fireError);

    var groups = new vis.DataSet();
    var items = new vis.DataSet();
    var container;
    var timeline;
    var options = {
        height: '100%',
        editable: {add: true, updateTime: true, updateGroup: false, remove: true},
        orientation: 'top',
        margin: 0,
        stack: false,
        multiselect: true,
        groupEditable: true,
        onMove: function (item, callback) {
            bufferService.push(items.get(item.id));
            callback(item);
            dataService.updateItem(item);
        }
    };

    setTimeout(function () {
        container = document.getElementById("js-timeline");
        timeline = new vis.Timeline(container, items, groups, options);
    }, 10);


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

    vm.showAddJob = function () {
        $mdDialog.show({
            locals: {dataToPass: vm.resources},
            controller: dialogsService.DialogController1,
            controllerAs: 'dad',
            templateUrl: '../dialogs/add_job.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        }).then(function (answer) {
            for (var i in answer.job.tasks) {
                delete answer.job.tasks[i].description;
                delete answer.job.tasks[i].name;
            }
            dataService.newJob(answer.job).then(function () {
                dataService.getJobs().then(function (data) {
                    vm.jobs = dataService.data;
                });
            })
        });
    }

    vm.showAddResource = function (ev) {
        $mdDialog.show({
            controller: dialogsService.DialogController4,
            controllerAs: 'dar',
            templateUrl: 'dialog_new_resource.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.newResource(answer).then(function () {
                dataService.getResources().then(function (data) {
                    vm.resources = dataService.data;
                });
            })
        });
    }

    vm.showNewOrder = function (ev) {
        $mdDialog.show({
            locals: {dataToPass: vm.jobs},
            controller: dialogsService.newOrderController,
            controllerAs: 'dno',
            templateUrl: 'dialog_new_order.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.newOrder(answer).then(function (data) {
                console.log(data);
                items.clear();
                groups.clear();
                items.add(dataService.items);
                groups.add(dataService.groups);
                timeline.fit({animation: true});
                vm.order = dataService.order;
            });
        });
    }

    vm.addJobsToOrder = function (ev) {
        $mdDialog.show({
            locals: {dataToPass: vm.jobs},
            controller: dialogsService.addJobOrderController,
            controllerAs: 'dno',
            templateUrl: 'dialog_add_job_order.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.addJobOrder(answer[0]).then(function (data) {
                console.log(data);
                items.clear();
                groups.clear();
                items.add(dataService.items);
                groups.add(dataService.groups);
                timeline.fit({animation: true});
                vm.order = dataService.order;
            });
        });
    }

    vm.editOrder = function (ev) {
        $mdDialog.show({
            locals: {dataToPass: vm.order},
            controller: dialogsService.editOrderController,
            controllerAs: 'DEO',
            templateUrl: 'dialog_edit_order.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.updateOrder(answer).then(function (data) {
                console.log(data);
                items.clear();
                groups.clear();
                items.add(dataService.items);
                groups.add(dataService.groups);
                timeline.fit({animation: true});
                vm.order = dataService.order;

            });
        });
    }


    vm.openOrder = function (ev) {
        $mdDialog.show({
            controller: dialogsService.openOrderController,
            controllerAs: 'NOC',
            templateUrl: 'dialog_open_order.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        }).then(function (answer) {
            dataService.openOrder(answer).then(function (data) {
                console.log(data);
                items.clear();
                groups.clear();
                items.add(dataService.items);
                groups.add(dataService.groups);
                timeline.fit({animation: true});
                vm.order = dataService.order;
                showSimpleToast('Order opened!');
            });
        });
    }

    vm.showSignIn = function (ev) {
        $mdDialog.show({
            locals: {parentScope: $scope},
            controller: dialogsService.DialogController3,
            controllerAs: 'dsi',
            templateUrl: '../dialogs/sign_in.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
    }


//  authService.authenticate({"username": "admin", "password": "admin"}).then(function() {
//	  vm.authenticated = authService.authenticated;
//	  vm.error = authService.error;
//	  if(vm.authenticated == true) {
//		dataService.whoAmI().then(function(data) {
//		vm.log = dataService.username;
//		vm.nam = dataService.name;
//		vm.sur = dataService.surname;
//		});
//		dataService.getResources().then(function(data){
//       		vm.resources = dataService.data;
//       	});
//		dataService.getJobs().then(function(data) {
//			vm.jobs = dataService.data;
//		});
//	  }
//  });
//

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
        $mdToast.show(
            $mdToast.simple()
                .textContent(text)
                .position('top right')
                .hideDelay(3000)
                .parent(angular.element(document.body))
        );
    }

    function lockTimeline() {
        vm.timelineLocked = !vm.timelineLocked;
        timeline.setOptions({
            editable: {
                add: !vm.timelineLocked,
                updateTime: !vm.timelineLocked,
                updateGroup: false,
                remove: !vm.timelineLocked
            }
        });
    }

    function showCurrentTime() {
        vm.timelineCurrentTime = !vm.timelineCurrentTime;
        timeline.setOptions({showCurrentTime: vm.timelineCurrentTime});
    }

    function setSnapping() {
        snapping = !snapping;
        if (snapping == false) {
            timeline.setOptions({snap: null})
        }
        else {
            timeline.setOptions({
                snap: function (date, scale, step) {
                    var hour = 60 * 1000;
                    return Math.round(date / hour) * hour;
                }
            })
        }
    }

    function focusTimeline() {
        timeline.fit({animation: true});
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
        bufferService.undo(items);
    }

    function redo() {
        bufferService.redo(items);
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


};
