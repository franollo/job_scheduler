angular
    .module('app')
    .service('authService', authService);

authService.$inject = ['$http'];

function authService($http) {
    var vm = this;
    var authenticated = false;
    vm.performLogin = performLogin;
    vm.performLoginRememberMe = performLoginRememberMe;
    vm.logout = logout;
    vm.isAuthenticated = isAuthenticated;

    function performLoginRememberMe(username, password) {
        return $http({
            method: 'GET',
            url: '/performLogin',
            params: {'remember-me': 'true', 'username': username, 'password': password}
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            throw error;
        })
    }

    function performLogin(username, password) {
        return $http({
            method: 'GET',
            url: '/performLogin',
            params: {'username': username, 'password': password}
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            throw error;
        })
    }

    function logout() {
        return $http({
            method: 'GET',
            url: '/performLogout'
        }).then(function (data) {
            return data.data;
        }).catch(function (error) {
            throw error;
        })
    }

    function isAuthenticated() {
        return authenticated;
    }
}