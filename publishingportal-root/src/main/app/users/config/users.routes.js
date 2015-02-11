'use strict';

// Setting up route
angular.module('users').config(['$stateProvider',
  function($stateProvider) {

    $stateProvider.
      state('login', {
        url: '/',
        templateUrl: 'users/views/login.view.html',
        data: {
          authorizedRoles: ['public']
        }
      })
      .state('users', {
        url: '/users',
        templateUrl: 'users/views/users.view.html',
        data: {
          authorizedRoles: ['user']
        }
      });
  }
]);
