angular.module('data', []).service('data', function($http) {

				var user = {};			
				this.whoAmI = function() {
					return $http.get("getdata/userinfo")
					.then(function(data) {
						user.username = data.data.login;
						user.name = data.data.name;
						user.surname = data.data.surname;
						return user;
					});
				};
				
				this.newOrder = function(order) {
					console.log(order);
					return $http({
			    		method: 'POST',
			    		dataType: 'json',
			    		url: "ajax/neworder",
			    		data: JSON.stringify(order)
			    	})
			    	.then(function(data){
			    		console.log(data);
			    		return data.data;
			    	});
				};
				
				this.newJob = function(job) {
					$http({
			    		method: 'POST',
			    		dataType: 'json',
			    		url: "ajax/newjob",
			    		data: JSON.stringify(job)
			    	})
			    	.success(function(data){
			    		console.log(data);
			    	}).error(function(data){
			    		console.log(data);
			    	});
				};
				
				this.newResource = function(resource) {
					$http({
			    		method: 'POST',
			    		dataType: 'json',
			    		url: "ajax/newresource",
			    		data: JSON.stringify(resource) 
			    	})
			    	.success(function(data){
			    		console.log(data);
			    	}).error(function(data){
			    		console.log(data);
			    	});
				};
				
				this.getResources = function() {
					return $http.get("getdata/resources")
			    	.then(function(data){
			    		return data;
			    	});
				};
				
				this.getJobs = function() {
					return $http.get("getdata/jobs")
			    	.then(function(data){
			    		console.log(data.data);
			    		return data;
			    	});
				}
		});