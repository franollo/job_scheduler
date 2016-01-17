var app = angular.module('MyApp', ['ngMaterial', 'auth', 'as.sortable', 'AngularPrint'])
  .config(function($mdThemingProvider) {
  $mdThemingProvider.theme('default')
    .primaryPalette('light-green', {'default': '900'})
    .accentPalette('grey', {'default': '500'})
    .warnPalette('red', {'default': '500'})
  })
  .filter('keyboardShortcut', function($window) {
    return function(str) {
      if (!str) return;
      var keys = str.split('-');
      var isOSX = /Mac OS X/.test($window.navigator.userAgent);

      var seperator = (!isOSX || keys.length > 2) ? '+' : '';

      var abbreviations = {
        M: isOSX ? '' : 'Ctrl',
        A: isOSX ? 'Option' : 'Alt',
        S: 'Shift'
      };

      return keys.map(function(key, index) {
        var last = index == keys.length - 1;
        return last ? key : abbreviations[key];
      }).join(seperator);
    };
  });



/**
TODO
*/

app.controller('DemoBasicCtrl', ['$scope', 
                                 '$mdDialog',
                                 '$mdToast',
                                 '$mdMedia',
                                 '$document',
                                 '$http',
                                 'auth',
                                 'data',
                                 'dialogs',
                                 function($scope, $mdDialog, $mdToast, $mdMedia, $document, $http, auth, data, dialogs) {       
	$scope.resources = [];
	$scope.jobs = [];
	$scope.selectedIndex = 1;
	$scope.order = {};

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
	    onMove: function(item, callback) {
	    	undo_buffer.push(items.get(item.id))
	    	undo_buffer = undo_buffer.slice(undo_buffer.length - 10, undo_buffer.length);
	    	callback(item);
	    	console.log(item);
	    	data.updateItem(item);
	    }
	}; 

    setTimeout(function(){
      container = document.getElementById("my-timeline");
      timeline = new vis.Timeline(container, items,groups, options);
    }, 10);

    $scope.hide = function() {
      $scope.isSmall = !$scope.isSmall;
    }

    $scope.showAddJob = function(ev) {
      $mdDialog.show({
        locals: {dataToPass: $scope.resources},
        controller: dialogs.DialogController1,
        controllerAs: 'dad',
        templateUrl: 'dialog_add_job.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true
      }).then(function(answer) {
    	  for (var i in answer.job.tasks) {
    		  delete answer.job.tasks[i].description;
    		  delete answer.job.tasks[i].name;
    	  }
    	  data.newJob(answer.job);
        });
    }
    
    $scope.showAddResource = function(ev) {
        $mdDialog.show({
            controller: dialogs.DialogController4,
            controllerAs: 'dar',
            templateUrl: 'dialog_new_resource.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true
          }).then(function(answer) {
              console.log(answer);
            });
      }

    $scope.showNewOrder = function(ev) {
      $mdDialog.show({
    	locals: {dataToPass: $scope.jobs},
        controller: dialogs.newOrderController,
        controllerAs: 'dno',
        templateUrl: 'dialog_new_order.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true
      }).then(function(answer) {
    	  data.newOrder(answer).then(function(data) {
    		  	console.log(data);
    		  	items.clear();
    		  	groups.clear();
	  			items.add(data.items);
	  			groups.add(data.groups);
	  			timeline.fit({animation: true});
	  			$scope.order = data.order;
    	  });
        });
    }
    
    $scope.editOrder = function(ev) {
        $mdDialog.show({
      	locals: {dataToPass: $scope.order},
          controller: dialogs.editOrderController,
          controllerAs: 'DEO',
          templateUrl: 'dialog_edit_order.html',
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true
        }).then(function(answer) {
      	  data.updateOrder(answer).then(function(data) {
      		  	items.clear();
      		  	groups.clear();
  	  			items.add(data.items);
  	  			groups.add(data.groups);
  	  			timeline.fit({animation: true});
  	  			$scope.order = data.order;
      	  });
          });
      }

    
    $scope.openOrder = function(ev) {
        $mdDialog.show({
          controller: dialogs.openOrderController,
          controllerAs: 'NOC',
          templateUrl: 'dialog_open_order.html',
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true
        }).then(function(answer) {
  		  data.openOrder(answer).then(function(data) {
  		  	console.log(data);
		  	items.clear();
		  	groups.clear();
  			items.add(data.items);
  			groups.add(data.groups);
  			timeline.fit({animation: true});
  			$scope.order = data.order;
  		  });
        });
    }
    
    $scope.showSignIn = function(ev) {
        $mdDialog.show({
          locals: {parentScope: $scope},
          controller: dialogs.DialogController3,
          controllerAs: 'dsi',
          templateUrl: 'dialog_sign_in.html',
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

    
  
  auth.authenticate({"username": "admin", "password": "admin"}).then(function() {
	  $scope.authenticated = auth.authenticated;
	  $scope.error = auth.error;
	  if($scope.authenticated == true) {
		data.whoAmI().then(function(data) {
		$scope.log = data.username;
		$scope.nam = data.name;
		$scope.sur = data.surname;
		});
		data.getResources().then(function(data){
       		$scope.resources = data.data;
       	});
		data.getJobs().then(function(data) {
			$scope.jobs = data.data;
		});
	  }
  });
 
  
  $scope.logout = function() {
	  auth.logout().then(function(){
  		$scope.log = "";
  		$scope.nam = "";
  		$scope.sur = "";
  		$scope.error = "";
  		$scope.authenticated = auth.authenticated;
	  });
	}
  
  $scope.timelineLocked = false;
  
  $scope.lockTimeline = function() {
	  $scope.timelineLocked = !$scope.timelineLocked;
	  console.log($scope.timelineLocked);
	  timeline.setOptions({editable:  {add: !$scope.timelineLocked, updateTime: !$scope.timelineLocked, updateGroup: false, remove: !$scope.timelineLocked}});
  }
  
  var snapping = true;
  $scope.setSnapping = function() {
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
  
  
  $scope.focusTimeline = function() {
	  timeline.fit({animation: true});
  }
  
  
  $scope.undo = function() {
	  var item = undo_buffer.pop();
	  if (item != undefined) {
		  redo_buffer.push(items.get(item.id));
		  redo_buffer = redo_buffer.slice(redo_buffer.length - 10, redo_buffer.length);
		  items.update(item);
	  }
  }
  
  $scope.redo = function() {
	  var item = redo_buffer.pop();
	  if (item != undefined) {
		  undo_buffer.push(items.get(item.id))
		  undo_buffer = undo_buffer.slice(undo_buffer.length - 10, undo_buffer.length);
		  items.update(item);
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
  
  
}]);




