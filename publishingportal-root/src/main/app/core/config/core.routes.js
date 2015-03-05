'use strict';

// Setting up route
angular.module('core').config(['$stateProvider', '$urlRouterProvider',
  function($stateProvider, $urlRouterProvider) {
    // Redirect to home view when route not found
    $urlRouterProvider.otherwise('/');

    $stateProvider.
      state('home', {
        url: '/',
        templateUrl: 'core/views/home.view.html',
        controller: 'ApplicationController',
        data: {
          authorizedRoles: ['user']
        }
      });
  }
]);
