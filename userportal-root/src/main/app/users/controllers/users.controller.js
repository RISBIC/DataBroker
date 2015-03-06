'use strict';

angular.module('users').controller('UsersController', ['$log', '$scope', '$rootScope', '$state', 'Global', 'AuthenticationService', 'AUTH_EVENTS', 'Users',
    function($log, $scope, $rootScope, $state, Global, AuthenticationService, AUTH_EVENTS, Users) {

        $scope.login = function(){

            $log.info($scope.user);

            Global.setSession(AuthenticationService.login($scope.user.email, $scope.user.password));

            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);


        };

      $scope.users = Users.all();

      $scope.user = Users.one(Number($state.params.id));

    }
]);
