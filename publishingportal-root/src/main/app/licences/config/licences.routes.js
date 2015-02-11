'use strict';

// Setting up route
angular.module('licences').config(['$stateProvider',
  function($stateProvider) {

    $stateProvider.
      state('licences', {
        url: '/licences',
        templateUrl: 'licences/views/licences.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      }).
      state('licence', {
        url: '/licences/:licenceId',
        templateUrl: 'licences/views/licence.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      });
  }
]);
