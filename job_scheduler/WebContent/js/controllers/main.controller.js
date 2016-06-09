angular
    .module('app')
    .controller('mainController', mainController);

mainController.$inject = ['$scope', '$document', '$mdDialog', '$mdToast', 'authService', 'dataService', 'dialogsService'];

function mainController($scope, $document, $mdDialog, $mdToast, authService, dataService, dialogsService) {
    var vm = this;
    vm.resources = [];
    vm.jobs = [];
    vm.selectedIndex = 1;
    vm.order = {};

        var groups = new vis.DataSet();
        var items = new vis.DataSet();
        var container;
        var timeline;
        var  options = {
            height: '100%',
            editable: {add: true, updateTime: true, updateGroup: false, remove: true},
            orientation: 'top',
            margin: 0,
            stack: false,
            multiselect: true,
            groupEditable: true,
            onMove: function(item, callback) {
                undo_buffer.push(items.get(item.id))
                undo_buffer = undo_buffer.slice(undo_buffer.length - 10, undo_buffer.length);
                callback(item);
                console.log(item);
                dataService.updateItem(item);
            }
        };

        setTimeout(function(){
            container = document.getElementById("my-timeline");
            timeline = new vis.Timeline(container, items,groups, options);
        }, 10);

        vm.hide = function() {
            vm.isSmall = !vm.isSmall;
        }

        vm.deleteJob = function(job) {
            dataService.deleteJob(job);
            vm.jobs.splice(vm.jobs.indexOf(job), 1);
        }

        vm.deleteResource = function(resource) {
            dataService.deleteResource(resource);
            vm.resources.splice(vm.resources.indexOf(resource), 1);
        }

        vm.updateJob = function(job) {
            dataService.updateJob(job);
        }

        vm.updateResource = function(resource) {
            dataService.updateResource(resource);
        }

        vm.showAddJob = function() {
            $mdDialog.show({
                locals: {dataToPass: vm.resources},
                controller: dialogsService.DialogController1,
                controllerAs: 'dad',
                templateUrl: '../dialogs/add_job.html',
                parent: angular.element(document.body),
                clickOutsideToClose:true
            }).then(function(answer) {
                for (var i in answer.job.tasks) {
                    delete answer.job.tasks[i].description;
                    delete answer.job.tasks[i].name;
                }
                dataService.newJob(answer.job).then(function() {
                    dataService.getJobs().then(function(data) {
                        vm.jobs = dataService.data;
                    });
                })
            });
        }

        vm.showAddResource = function(ev) {
            $mdDialog.show({
                controller: dialogsService.DialogController4,
                controllerAs: 'dar',
                templateUrl: 'dialog_new_resource.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true
            }).then(function(answer) {
                dataService.newResource(answer).then(function() {
                    dataService.getResources().then(function(data) {
                        vm.resources = dataService.data;
                    });
                })
            });
        }

        vm.showNewOrder = function(ev) {
            $mdDialog.show({
                locals: {dataToPass: vm.jobs},
                controller: dialogsService.newOrderController,
                controllerAs: 'dno',
                templateUrl: 'dialog_new_order.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true
            }).then(function(answer) {
                dataService.newOrder(answer).then(function(data) {
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

        vm.addJobsToOrder = function(ev) {
            $mdDialog.show({
                locals: {dataToPass: vm.jobs},
                controller: dialogsService.addJobOrderController,
                controllerAs: 'dno',
                templateUrl: 'dialog_add_job_order.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true
            }).then(function(answer) {
                dataService.addJobOrder(answer[0]).then(function(data) {
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

        vm.editOrder = function(ev) {
            $mdDialog.show({
                locals: {dataToPass: vm.order},
                controller: dialogsService.editOrderController,
                controllerAs: 'DEO',
                templateUrl: 'dialog_edit_order.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true
            }).then(function(answer) {
                dataService.updateOrder(answer).then(function(data) {
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


        vm.openOrder = function(ev) {
            $mdDialog.show({
                controller: dialogsService.openOrderController,
                controllerAs: 'NOC',
                templateUrl: 'dialog_open_order.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true
            }).then(function(answer) {
                dataService.openOrder(answer).then(function(data) {
                    console.log(data);
                    items.clear();
                    groups.clear();
                    items.add(dataService.items);
                    groups.add(dataService.groups);
                    timeline.fit({animation: true});
                    vm.order = dataService.order;
                    showSimpleToast2('Order opened!');
                });
            });
        }

        vm.showSignIn = function(ev) {
            $mdDialog.show({
                locals: {parentScope: $scope},
                controller: dialogsService.DialogController3,
                controllerAs: 'dsi',
                templateUrl: '../dialogs/sign_in.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true
            });
        }

        var showSimpleToast = function() {
            $mdToast.show({
                templateUrl: 'toast-template.html',
                parent: angular.element(document.body),
                hideDelay: 3000,
                position: 'top right'
            });
        };

        var showSimpleToast2 = function(text) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(text)
                    .position('top right')
                    .hideDelay(3000)
                    .parent(angular.element(document.body))
            );
        };



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

        vm.logout = function() {
            authService.logout().then(function(){
                vm.log = "";
                vm.nam = "";
                vm.sur = "";
                vm.error = "";
                vm.authenticated = authService.authenticated;
            });
        }

        vm.timelineLocked = false;
        vm.timelineCurrentTime = true;

        vm.lockTimeline = function() {
            vm.timelineLocked = !vm.timelineLocked;
            timeline.setOptions({editable:  {add: !vm.timelineLocked, updateTime: !vm.timelineLocked, updateGroup: false, remove: !vm.timelineLocked}});
        }

        vm.showCurrentTime = function() {
            vm.timelineCurrentTime = !vm.timelineCurrentTime;
            timeline.setOptions({showCurrentTime: vm.timelineCurrentTime});
        }

        var snapping = true;
        vm.setSnapping = function() {
            snapping = !snapping;
            if(snapping == false) {
                timeline.setOptions({snap: null})
            }
            else {
                timeline.setOptions({snap: function (date, scale, step) {
                    var hour = 60 * 1000;
                    return Math.round(date / hour) * hour;
                }
                })
            }
        }


        vm.focusTimeline = function() {
            timeline.fit({animation: true});
        }


        vm.undo = function() {
            var item = undo_buffer.pop();
            if (item != undefined) {
                redo_buffer.push(items.get(item.id));
                redo_buffer = redo_buffer.slice(redo_buffer.length - 10, redo_buffer.length);
                item.start = new Date(item.start);
                item.end = new Date(item.end);
                items.update(item);
                dataService.updateItem(item);
            }
        }

        vm.redo = function() {
            var item = redo_buffer.pop();
            if (item != undefined) {
                undo_buffer.push(items.get(item.id))
                undo_buffer = undo_buffer.slice(undo_buffer.length - 10, undo_buffer.length);
                item.start = new Date(item.start);
                item.end = new Date(item.end);
                items.update(item);
                dataService.updateItem(item);
            }
        }

        var undo_buffer = new Array();
        var redo_buffer = new Array();

        var read = -1;
        var save = 0;
        var bufferSize = 0

        var push = function(item) {
            undo_buffer[save] = item;
            if(bufferSize<10) {
                bufferSize++;
            }
            save = (save+1) % 10;
            read = (read+1) % 10;
        }

        var pop = function() {
            bufferSize--;
            if(bufferSize = 0) {
                read = -1;
                save = 0;
            }
            save = (save-1) % 10;
            read = (read-1) % 10;
            return
        }


    };
