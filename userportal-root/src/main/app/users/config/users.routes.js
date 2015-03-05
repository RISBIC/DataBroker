'use strict';

// Setting up route
angular.module('users').config(['$stateProvider',
  function($stateProvider) {

    $stateProvider.
      state('users', {
        url: '/users',
        templateUrl: 'users/views/users.view.html',
        data: {
          authorizedRoles: ['users']
        }
      }).
        state('login', {
            url: '/users/login',
            templateUrl: 'users/views/login.view.html',
            controller: 'UsersController',
            data: {
                authorizedRoles: ['public']
            }
        });
  }
]);
