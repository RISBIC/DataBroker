'use strict';

/**
 * @ngdoc overview
 * @name mainApp
 * @description
 * # mainApp
 *
 * Main module of the application.
 */

angular.module('authentication', []);
angular.module('core', []);
angular.module('listings', []);
angular.module('users', []);

angular.module('authentication').constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

angular.module('authentication').constant('USER_ROLES', {
    user: 'user',
    admin: 'admin',
    public: 'public'
});

angular.module('authentication').factory('escHttpInterceptor', ['$rootScope', '$q', '$log', 'AUTH_EVENTS', function($rootScope, $q, $log, AUTH_EVENTS) {
    return {
        request: function (config) {
            // do something on success
            //$log.info(config);
            return config;
        },
        requestError: function (rejection) {
            // do something on error
            //$log.error(rejection);
            return $q.reject(rejection);
        },
        response: function (response) {
            // do something on success
            //$log.info(response);
            return response;
        },
        responseError: function (rejection) {
            // do something on error
            //$log.error(rejection);

            if(rejection.status === 401){
                $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
            }

            return $q.reject(rejection);
        }
    };
}]);

angular.module('authentication').config(['$httpProvider', function ($httpProvider){

    $httpProvider.interceptors.push('escHttpInterceptor');

}]);

var modules = ['ngAnimate', 'ngAria', 'ngCookies', 'ngMessages', 'ngResource', 'ngRoute', 'ngSanitize', 'ngTouch', 'ui.router', 'authentication', 'core', 'listings', 'users'];

var app = angular.module('mainApp', modules);

app.constant('CONFIG', {
    apiURL: 'http://localhost:8080/website-api/rest/'
});

app.run(function ($log, $rootScope, $state, $window, AUTH_EVENTS, AuthenticationService, Global) {

    $rootScope.global = Global;

    $rootScope.$on('$stateChangeStart', function (event, next) {

        var authorizedRoles = next.data.authorizedRoles;

        if(next.name === 'login'){
            if(Global.session !== null){
                event.preventDefault();}
        }
        else {
            if (!AuthenticationService.isAuthorized(authorizedRoles)) {
                event.preventDefault();

                if (AuthenticationService.isAuthenticated()) {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
                } else {
                    $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
                }
            }
        }

    });
});