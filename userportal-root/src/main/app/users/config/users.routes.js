'use strict';

// Setting up route
angular.module('users').config(['$stateProvider',
  function($stateProvider) {

    $stateProvider.
      state('users', {
        url: '/users',
        controller: 'UsersController',
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
        }).
      state('user', {
        url: '/users/:id',
        templateUrl: 'users/views/user.view.html',
        controller: 'UsersController',
        data: {
          authorizedRoles: ['users']
        }
      });
  }
]);
