angular.module('Login').controller('Controller', ['$scope', 'auth', function($scope, auth) {  
  auth.authenticate({"username": "admin", "password": "admin"}).then(function() {
	  if(auth.authenticated == true) {
		  $window.location.href = '/job_scheduler/index.jsp';
		}
	  });
}]);