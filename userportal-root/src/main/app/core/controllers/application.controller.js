'use strict';

angular.module('core').controller('ApplicationController', ['$log', '$scope', '$state', '$window', 'Global', 'AuthenticationService', 'AUTH_EVENTS',
    function($log, $scope, $state, $window, Global, AuthenticationService, AUTH_EVENTS) {

        $scope.button = function(){

            AuthenticationService.logout();

            $window.sessionStorage.removeItem('userPortalSession');

            $state.go('login')

        };

        $scope.$on(AUTH_EVENTS.loginSuccess, function(){

            $log.info('Login Success: Storing Session');

            $window.sessionStorage.setItem('userPortalSession', JSON.stringify(Global.session));

            $log.info('userPortalSession');
            $log.info(JSON.parse($window.sessionStorage.getItem('userPortalSession')));

            $state.go('home');

        }, true);

        $scope.$on(AUTH_EVENTS.notAuthenticated, function(){

            $log.warn('Not Authenticated Event');

            $state.go('login');
        }, true);

        $scope.loggedin = function() {
            console.log(Global.getSession());
            return Global.getSession();
        };

        $scope.isAuthorized = function(roles) {
            return AuthenticationService.isAuthorized(roles);
        };

        $scope.logout = function() {
            AuthenticationService.logout();
        }

    }
]);