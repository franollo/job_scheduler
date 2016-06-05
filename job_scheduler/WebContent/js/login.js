var app = angular.module('LoginPage', ['ngMaterial', 'auth'])
  .config(function($mdThemingProvider) {
  $mdThemingProvider.theme('default')
    .primaryPalette('light-green', {'default': '900'})
    .accentPalette('grey', {'default': '500'})
    .warnPalette('red', {'default': '500'})
  });

app.controller('Controller', ['$scope', '$window', 'auth', function($scope, $window, auth) {  
  auth.authenticate({"username": "admin", "password": "admin"}).then(function() {
	  if(auth.authenticated == true) {
		  $window.location.href = '/job_scheduler/index.jsp';
		}
	  });
}]);