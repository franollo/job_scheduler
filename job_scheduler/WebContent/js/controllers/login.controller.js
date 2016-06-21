angular
    .module('app')
    .controller('loginController', loginController);

loginController.$inject = ['authService', '$location'];

function loginController(authService, $location) {
    var vm = this;
    vm.credentials = {};
    vm.signIn = signIn;

    function signIn() {
        if (authService.authenticate() == true) {
            $location.path('/application');
        }
    }
}