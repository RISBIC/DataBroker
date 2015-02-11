'use strict';

angular.module('core').controller('ApplicationController', ['$log', '$scope', '$state', '$window', 'Global', 'AuthenticationService', 'AUTH_EVENTS',
  function($log, $scope, $state, $window, Global, AuthenticationService, AUTH_EVENTS) {

    $scope.button = function(){

      AuthenticationService.logout();

      $window.sessionStorage.removeItem('publishingSession');

      $state.go('login')

    };

    $scope.$on(AUTH_EVENTS.loginSuccess, function(){

      $log.info('Login Success: Storing Session');

      $window.sessionStorage.setItem('publishingSession', JSON.stringify(Global.session));

      $log.info('publishingSession');
      $log.info(JSON.parse($window.sessionStorage.getItem('publishingSession')));

      $state.go('home');

    }, true);

    $scope.$on(AUTH_EVENTS.notAuthenticated, function(){

      $log.warn('Not Authenticated Event');

      $state.go('login');
    }, true);

  }
]);
