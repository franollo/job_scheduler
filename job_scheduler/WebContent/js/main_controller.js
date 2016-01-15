var app = angular.module('MyApp', ['ngMaterial', 'auth', 'as.sortable',])
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
                                 function($scope, $mdDialog, $mdToast, $mdMedia, $document, $http, auth, data, dialogs) 
{       
        $scope.orderTitle = '';
        this.inp;
        $scope.resources = [];
        $scope.jobs = [];
        $scope.selectedIndex = 2;
       	
       	var items = [];
       	var groups = [];

        

      var dataSet = new vis.DataSet();
      var job;
      var id_index = 0;
      var jobs = [];

      this.newDate = {
       value: new Date(1970, 0, 1, 0, 0, 0)
      };

      this.CurrentDate = {
       value: new Date()
      };

      var container;
      var timeline;
      var options;

    setTimeout(function(){
      container = document.getElementById("my-timeline");
      options = {
        height: '100%',
        editable: true,
        orientation: 'top',
        margin: 0,
        stack: false
      };
      var items = new vis.DataSet();
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
    	locals: {dataToPass: $scope.jobs, dataToPass2: timeline},
        controller: dialogs.DialogController2,
        controllerAs: 'dno',
        templateUrl: 'dialog_new_order.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true
      }).then(function(answer) {
          $scope.orderTitle = answer.orderName;
          console.log(answer);
        });
    }
    
    $scope.openOrder = function(ev) {
        $mdDialog.show({
      	locals: {timeline: timeline},
          controller: dialogs.openOrderController,
          controllerAs: 'NOC',
          templateUrl: 'dialog_open_order.html',
          parent: angular.element(document.body),

          targetEvent: ev,
          clickOutsideToClose:true
        }).then(function(answer) {
            $scope.orderTitle = answer.orderName;
            console.log(answer);
            showSimpleToast();
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
			console.log(data.data);
			console.log(jobs);
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
	  timeline.setOptions({editable: !$scope.timelineLocked});
  }
  
  $scope.focusTimeline = function() {
	  timeline.fit({animation: true});
  }
  
  
  var createOrder = function() {
	  
  }
  
  
}]);




/**
* Spaghetti!!
*/

  function insertItems(jobTasks, jobName, jobColor) {
    var startTime;
    var endTime;
    var jobsIds = [];
    var id_index_tmp = id_index;
    var min = Number.POSITIVE_INFINITY;

    if(dataSet.length == 0) {
      endTime = new Date();
      jobTasks.sort(compare);
      for (i=1, j=0; i<=jobTasks.length; i++, j++) {
        if (jobTasks[j].hh_dur > 0 || jobTasks[j].mm_dur > 0 || jobTasks[j].ss_dur > 0) {
          startTime = new Date(endTime.getTime());
          endTime = new Date(startTime.getTime());
          endTime.setMinutes(endTime.getMinutes() + jobTasks[j].mm_dur);
          endTime.setHours(endTime.getHours() + jobTasks[j].hh_dur);
          endTime.setSeconds(endTime.getSeconds() + jobTasks[j].ss_dur);
          endTime.setMilliseconds(0);
          startTime.setMilliseconds(0);
          jobsIds.push({id: id_index, start: startTime, end: endTime, order: jobTasks[j].order, group: jobTasks[j].id});
          dataSet.add({id: id_index, content: jobName, start: startTime, end: endTime, group: jobTasks[j].id});
          id_index++;
        }
        else {
          jobsIds.push({id: -1, start: startTime, end: endTime});
        }
      }
      jobs.push(jobsIds);
    }
    else {

      endTime = dataSet.max('end').end;

      jobTasks.sort(compare);
      for (i=1, j=0; i<=jobTasks.length; i++, j++) {
        if (jobTasks[j].hh_dur > 0 || jobTasks[j].mm_dur > 0 || jobTasks[j].ss_dur > 0) {
          startTime = new Date(endTime.getTime());
          endTime = new Date(startTime.getTime());
          endTime.setMinutes(endTime.getMinutes() + jobTasks[j].mm_dur);
          endTime.setHours(endTime.getHours() + jobTasks[j].hh_dur);
          endTime.setSeconds(endTime.getSeconds() + jobTasks[j].ss_dur);
          endTime.setMilliseconds(0);
          startTime.setMilliseconds(0);
          jobsIds.push({id: id_index, start: startTime, end: endTime, order: jobTasks[j].order, group: jobTasks[j].id});
          id_index++;
        }
        else {
          jobsIds.push({id: -1, start: startTime, end: endTime});
        }
      }

      jobsIds.sort(compare_restore);
      jobs.push(jobsIds);

      for (i=1, j=0; i<=jobTasks.length; i++, j++) {
        if(jobs[jobs.length-1][j].id >= 0 && jobs[jobs.length-2][j].id >= 0) {
          var min_tmp = Math.abs(jobs[jobs.length-2][j].end - jobs[jobs.length-1][j].start);
          if (min_tmp < min) {
            min = min_tmp;
          }
        }
      }

      for (i=0, j=1; i<jobsIds.length; j++, i++){
        if (jobsIds[i].id >= 0) {
          jobsIds[i].start.setMilliseconds(jobsIds[i].start.getMilliseconds() - min);
          jobsIds[i].end.setMilliseconds(jobsIds[i].end.getMilliseconds() - min);
          dataSet.add({
            id: jobsIds[i].id, 
            content: jobName, 
            start: jobsIds[i].start, 
            end: jobsIds[i].end, 
            group: jobsIds[i].group, 
            className: jobColor});
        }
      }
    }
    timeline.setItems(dataSet);
    clearGroups();
  }

  function compare(a,b) {
    if (a.job_order < b.job_order)
      return -1;
    if (a.job_order > b.job_order)
      return 1;
    return 0;
  }

  function compare_restore(a,b) {
    if (a.order < b.order)
      return -1;
    if (a.order > b.order)
      return 1;
    return 0;
  }


