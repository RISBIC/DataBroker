'use strict';

angular.module('users').config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push([
        '$injector',
        function ($injector) {
            return $injector.get('AuthenticationInterceptor');
        }
    ]);
}]);

angular.module('users').factory('AuthenticationInterceptor', ['$log', '$rootScope', '$q', '$window', 'AUTH_EVENTS', 'CONFIG', function ($log, $rootScope, $q, $window, AUTH_EVENTS, CONFIG) {
    return {
        request: function(config) {

            var sessionInfo = JSON.parse($window.sessionStorage.getItem('userPortalSession'));

            if (config.url.indexOf(CONFIG.apiURL) > -1 && sessionInfo !== null) {
                config.headers.Authorization = sessionInfo.sessionToken;
            }
            return config;
        },
        responseError: function (response) {

            $rootScope.$broadcast({
                401: AUTH_EVENTS.notAuthenticated,
                403: AUTH_EVENTS.notAuthorized,
                419: AUTH_EVENTS.sessionTimeout,
                440: AUTH_EVENTS.sessionTimeout
            }[response.status], response);

            return $q.reject(response);
        }
    };
}]);