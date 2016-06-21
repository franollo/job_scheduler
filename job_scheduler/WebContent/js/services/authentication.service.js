angular
    .module('app')
    .service('authService', authService);

authService.$inject = ['$http'];

function authService($http) {
    var vm = this;
    var authenticated = false;
    vm.authenticate = sendRequest;
    vm.logout = logout;
    vm.isAuthenticated = isAuthenticated;

    function authenticate() {
        //temp mock:

        console.log();
        return true;
/*
        if (username != undefined && password != undefined) {
            return $http.get('user', {
                'headers': {authorization: "Basic " + btoa(username + ":" + password)}
            }).then(function (data) {
                if (data.status == 200) {
                    authenticated = data.authenticated;
                }
            }).catch(function (error) {
                console.log(error);
                authenticated = false;
            })
        }*/
    }

    function sendRequest() {
        return $http({
            method: 'GET',
            url: '/performLogin',
            params: {'remember-me' : 'true', 'no-elo' : 'wartosc' }
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            throw error;
        })
    }

    function logout() {
        return $http.post('logout', {}).then(function (data) {
            authenticated = false;
        });
    }

    function isAuthenticated() {
        return authenticated;
    }
}