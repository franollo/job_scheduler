angular
    .module('app')
    .controller('loginController', loginController);

loginController.$inject = ['authService'];

function loginController(authService) {
    var vm = this;
    vm.credentials = {};
    vm.signIn = signIn;

    function signIn() {
        if (authService.authenticate(vm.credentials) == true) {
            window.location = 'index.html';
        }
    }
}