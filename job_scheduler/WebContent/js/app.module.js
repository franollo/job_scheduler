var appModule = angular
    .module('app', ['ngMaterial', 'ngRoute'])
    .config(app);

function app($mdThemingProvider, $routeProvider, $httpProvider, $interpolateProvider) {
    $mdThemingProvider
        .theme('default')
        .primaryPalette('light-green', {'default': '900'})
        .accentPalette('grey', {'default': '500'})
        .warnPalette('red', {'default': '500'});

    $httpProvider
        .interceptors
        .push('httpRequestInterceptor');
    
    $routeProvider
        .when('/login', {
            templateUrl: 'html/views/login.html',
            controller: 'loginController as ctrl'
        })
        .when('/application', {
            templateUrl: 'html/views/app.html',
            controller: 'mainController as ctrl'
        })
        .when('/401', {
            templateUrl: 'html/views/401.html'
        })
        .otherwise({
            redirectTo: 'login'
        })
}





