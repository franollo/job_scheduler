angular.module('app').service('authService', function($http) {
    var authenticated = false;
    var error = false;

    this.authenticate = function(credentials) {
        var headers = credentials && credentials.username ? {
            authorization: "Basic "
            + btoa(credentials.username + ":"
                + credentials.password)
        } : {};
        return $http.get('user', {
            'headers' : headers
        }).then(function(data) {
            if(data.status == 200) {
                authenticated = data.authenticated;
                error = false;
            }
        }, function(data) {
            if(data.status != 200) {
                authenticated = false;
                error = true;
            }
        });
    }

    this.logout = function() {
        return $http.post('logout', {}).then(function(data) {
            if(data.status == 200) {
                error = false;
            }
            else {
                error = true;
            }
            authenticated = false;
        });
    }

    this.isAuthenticated = function() {
        return authenticated;
    }
})