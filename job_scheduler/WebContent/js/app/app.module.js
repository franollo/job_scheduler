var app = angular.module('app', ['ngMaterial']);

app.config(function($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('light-green', {'default': '900'})
        .accentPalette('grey', {'default': '500'})
        .warnPalette('red', {'default': '500'})
});





