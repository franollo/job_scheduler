var app = angular.module('MyApp', ['ngMaterial', 'auth', 'data', 'as.sortable',])
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
                                 '$mdMedia', 
                                 '$http',
                                 'auth',
                                 'data',
                                 function($scope, $mdDialog, $mdMedia, $http, auth, data) 
{       
        $scope.orderTitle = '';
        this.inp;
        $scope.resources = [];
        $scope.jobs = [];
        $scope.selectedIndex = 2;
       	
       	var items = [];
       	var groups = [];
      /*  var groups = [
        {id: 1, content: 'Maszyna1', order: 1, hh_dur:3, mm_dur:0, ss_dur:0, job_order: 1},
        {id: 2, content: 'Maszyna2', order: 2,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 2},
        {id: 3, content: 'Maszyna3', order: 3,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 3},
        {id: 4, content: 'Maszyna4', order: 4,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 4},
        {id: 5, content: 'Maszyna5', order: 5,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 5},
        {id: 6, content: 'Maszyna6', order: 1, hh_dur:3, mm_dur:0, ss_dur:0, job_order: 1},
        {id: 7, content: 'Maszyna7', order: 2,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 2},
        {id: 8, content: 'Maszyna8', order: 3,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 3},
        {id: 9, content: 'Maszyna9', order: 4,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 4},
        {id: 10, content: 'Maszyna10', order: 5,hh_dur:3, mm_dur:0, ss_dur:0, job_order: 5}
      ];*/
        

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
        editable: {remove: true, updateTime: true},
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
        controller: DialogController1,
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
            controller: DialogController4,
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
        controller: DialogController2,
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
    
    $scope.showSignIn = function(ev) {
        $mdDialog.show({
          locals: {parentScope: $scope},
          controller: DialogController3,
          controllerAs: 'dsi',
          templateUrl: 'dialog_sign_in.html',
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true
        });
      }

    
    /**
     * Create new job controller
     */
    
  function DialogController1($scope, $mdDialog, dataToPass) {
	  $scope.dragControlListeners = {
	          containment: '#board',//optional param.
	          allowDuplicates: true //optional param allows duplicates to be dropped.
	  };
	this.tasks = dataToPass;
	var sth = false;
	$scope.answer = function() {
		$scope.job.tasks = JSON.parse(JSON.stringify($scope.selected));
		$mdDialog.hide({"job": $scope.job});
		$scope.job = {};
		$scope.selected = [];
	}
	
	$scope.cancel = function() {
		$scope.job = {};
		$scope.selected = [];
		$mdDialog.cancel();
	}
	$scope.toggle = function (item, list) {
	    var idx = list.indexOf(item);
	    if (idx > -1) list.splice(idx, 1);
	    else list.push(item);
	};
	  $scope.exists = function (item, list) {
	    return list.indexOf(item) > -1;
	  };
	  $scope.selected = [];
	  $scope.job = {};
  }


  /**
   * Create new order
   */  
    
    
  function DialogController2($scope, $mdDialog, dataToPass, dataToPass2) {
	var parentThis = this;
	var timelinePointer = dataToPass2;
	this.jobs = dataToPass;
	this.orderName = "";
	this.description = "";
	this.selected = [];
	this.startTime = new Date();
	this.startDate = new Date();
	this.startTime.setMilliseconds(0);
	this.startDate.setMilliseconds(0);
    $scope.answer = function() {
      var start = new Date(parentThis.startDate.getFullYear(), parentThis.startDate.getMonth(), parentThis.startDate.getDate(), 
    		  parentThis.startTime.getHours(), parentThis.startTime.getMinutes(), parentThis.startTime.getSeconds());
      $mdDialog.hide({"orderName": parentThis.orderName, 
    	  			"startTime": start, 
    	  			"startDate": parentThis.startDate});
      data.newOrder({"name": parentThis.orderName, 
    		  		"description": parentThis.description, 
    		  		"startDate": start,
    		  		"endDate": parentThis.startTime,
    		  		"jobs": parentThis.selected}).then(function(data) {
    		  			timelinePointer.setGroups(data.groups);
    		  			timelinePointer.setItems(data.items);
    		  		})
    }

    $scope.cancel = function() {
      console.log('cancel');
      $mdDialog.cancel();
    }
    $scope.toggle = function (item, list) {
	    var idx = list.indexOf(item);
	    if (idx > -1) list.splice(idx, 1);
	    else list.push(item);
	};
	  $scope.exists = function (item, list) {
	    return list.indexOf(item) > -1;
	  };
  }
  
  /** 
   * Sign In dialog controller 
   */
  
  function DialogController3($scope, $mdDialog, parentScope) {
	var parent_this = this;
	this.credentials = {};
    $scope.answer = function() {
      $mdDialog.hide(answer);
    }

    $scope.cancel = function() {
      $mdDialog.cancel();
    }
    
    $scope.signIn = function() {
        auth.authenticate(parent_this.credentials, function() {}).then(function() {
        	parentScope.authenticated = auth.authenticated;
          	if (parentScope.authenticated == true) {
        		$scope.error = auth.error;
        		$mdDialog.hide();
        		data.whoAmI().then(function(data) {
        			parentScope.log = data.username;
        			parentScope.nam = data.name;
        			parentScope.sur = data.surname;
        		});
        	}
        	else {
        		$scope.error = true;
        	}
        });
    }
  }


/**
 * New resource
 */
  function DialogController4($scope, $mdDialog) {
		var parentThis = this;
		this.resourceName = "";
		this.description = "";
	    $scope.answer = function() {
	      $mdDialog.hide();
	      data.newResource({"name": parentThis.resourceName, 
	  			"description": parentThis.description});
	    }
	    $scope.cancel = function() {
	      console.log('cancel');
	      $mdDialog.cancel();
	    }
	  }
  
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


