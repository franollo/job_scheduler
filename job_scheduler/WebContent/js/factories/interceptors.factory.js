angular
    .module('app')
    .factory('httpRequestInterceptor', function ($q, $location) {
        return {
            'responseError': function(rejection) {
                // do something on error
                if(rejection.status === 401){
                    $location.path('/401');
                }
                return $q.reject(rejection);
            }
        };
    });