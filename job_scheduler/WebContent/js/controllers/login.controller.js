angular.module('app').controller('loginController', loginController);

function loginController() {
    var vm = this;
    vm.login = "";
    vm.password = "";
    vm.signIn = signIn;

    function signIn() {
    }
}