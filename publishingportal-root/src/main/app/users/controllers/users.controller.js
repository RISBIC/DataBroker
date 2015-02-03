'use strict';

angular.module('users').controller('UsersController', ['$log', '$scope', '$state', 'Global',
  function($log, $scope, $state, Global) {

    $scope.login = function(){

      $log.info($scope.user);

      var user = {
        email: $scope.user.email,
        roles: ['user']
      };

      Global.setSession(user);

      $state.go('home');

    };

  }
]);
