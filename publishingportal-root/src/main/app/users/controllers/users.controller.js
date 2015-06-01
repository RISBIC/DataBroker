'use strict';

angular.module('users').controller('UsersController', ['$log', '$scope', '$rootScope', '$state', 'md5', 'Global', 'AuthenticationService', 'AUTH_EVENTS', 'CONFIG',
  function($log, $scope, $rootScope, $state, md5, Global, AuthenticationService, AUTH_EVENTS, CONFIG) {

    $scope.login = function(){

      $log.info($scope.user);

      Global.setSession(AuthenticationService.login($scope.user.email, $scope.user.password));

      if($scope.user.email === CONFIG.user.email && md5.createHash($scope.user.password) === CONFIG.user.password){
        $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
      }
      else {
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
      }
    };
  }
]);
