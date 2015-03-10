'use strict';

angular.module('core').controller('ApplicationController', ['$log', '$scope', '$state', '$window', 'Global', 'AuthenticationService', 'AUTH_EVENTS', 'Search',
    function($log, $scope, $state, $window, Global, AuthenticationService, AUTH_EVENTS, Search) {

        $scope.button = function(){

            AuthenticationService.logout();

            $window.sessionStorage.removeItem('userPortalSession');

            $state.go('login');

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
            return Global.getSession();
        };

        $scope.isAuthorized = function(roles) {
            return AuthenticationService.isAuthorized(roles);
        };

        $scope.login = function() {
            $state.go('login');
        };

        $scope.logout = function() {
            AuthenticationService.logout();
        };

        $scope.searchString = Search.query;

        $scope.$watch('searchString', function(val) {
            Search.query = val;
        });

        $scope.submitSearch = function($event) {

            if ($event.keyCode === 13) {
                $state.go('listings', {query: $scope.searchString});
            }
        };

      $scope.$on('AUTH_EVENTS.logoutSuccess', function(){
        $scope.searchString = '';

      });

    }
]);
