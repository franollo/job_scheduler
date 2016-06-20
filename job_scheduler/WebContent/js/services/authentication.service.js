angular
    .module('app')
    .service('authService', authService);

authService.$inject = ['$http'];

//https://discuss.zendesk.com/hc/en-us/articles/202653598-Performing-an-AJAX-login-with-Spring-Security-3-0-2002229-#sample

function authService($http) {
    var vm = this;
    var authenticated = false;
    vm.authenticate = authenticate;
    vm.logout = logout;
    vm.isAuthenticated = isAuthenticated;

    function authenticate(username, password) {
        //temp mock:

        // console.log(credentials);
        // return true;

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
        }
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