angular
    .module('app')
    .controller('loginController', loginController);

loginController.$inject = ['authService', '$location'];

function loginController(authService, $location) {
    var vm = this;
    vm.credentials = {username: 'admin', password: 's3cret'};
    vm.rememberMe = false;
    vm.signIn = signIn;
    vm.logout = authService.logout;
    vm.badCredentials = false;

    function signIn() {
        if (vm.rememberMe == false) {
            authService.performLogin(vm.credentials.username, vm.credentials.password)
                .then(handleLogin)
                .catch(handleBadCredentials);
        }
        else {
            authService.performLoginRememberMe(vm.credentials.username, vm.credentials.password)
                .then(handleLogin)
                .catch(handleBadCredentials);
        }
    }

    function handleBadCredentials(error) {
        if (error.status == 422) {
            vm.badCredentials = true;
        }
    }

    function handleLogin() {
        vm.badCredentials = false;
        $location.path('/application');
    }
}
