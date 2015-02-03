'use strict';

angular.module('core').controller('ApplicationController', ['$log', '$scope', '$state', 'AuthenticationService', 'AUTH_EVENTS',
  function($log, $scope, $state, AuthenticationService, AUTH_EVENTS) {

    $scope.welcome = 'Hello World!';

    $scope.button = function(){

      AuthenticationService.logout();

      $window.sessionStorage.removeItem('escSession');

      $state.go('login')

    };

    $scope.$on(AUTH_EVENTS.loginSuccess, function(){

      $log.info('Login Success: Storing Session');

      $window.sessionStorage.setItem('escSession', JSON.stringify(Global.session));

      $log.info('escSession');
      $log.info(JSON.parse($window.sessionStorage.getItem('escSession')));

    }, true);


  }
]);
