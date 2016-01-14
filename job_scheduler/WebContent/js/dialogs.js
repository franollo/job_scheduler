angular.module('MyApp').service('dialogs',['$http','data',function($http,data) {

		this.DialogController1 = function ($scope, $mdDialog, dataToPass) {
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
		
	
  this.DialogController2 = function($scope, $mdDialog, dataToPass, dataToPass2) {
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
	    		  			timeline.fit({animation: true});
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
  
  
  this.DialogController3 = function($scope, $mdDialog, parentScope) {
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
  
  this.DialogController4 = function($scope, $mdDialog) {
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
  
  this.openOrderController = function($scope, $mdDialog, timeline) {
	  var parentThis = this;
	  $scope.orders = [];
	  $scope.selectedOrderId;
	  data.getOrders().then(function(data) {
		  for (var i = 0; i < data.length; i++) {
					if (!$scope.$$phase) {
					    logic(data[i]);
					} else {
					    $timeout(logic(data[i]));
					}
		  }
		  console.log($scope.orders);
	  });
	  $scope.answer = function() {
		  $mdDialog.hide({"orderName": parentThis.orderName});
		  data.openOrder($scope.selectedOrderId).then(function(data) {
		  			timeline.setGroups(data.groups);
		  			timeline.setItems(data.items);
		  			timeline.fit({animation: true});
		  });
	  }
	  $scope.cancel = function() {
		  $mdDialog.cancel();
	  }
	  
	  function logic(item) {
		  $scope.orders.push(item);
	  }
	  
	  $scope.select = function(id) {
		  $scope.selectedOrderId = id;
	  }
	  
  }
}]);