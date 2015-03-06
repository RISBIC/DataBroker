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
      }).
      state('templates', {
        url: '/templates',
        templateUrl: 'licences/views/templates.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      }).
      state('template', {
        url: '/templates/:templateId',
        templateUrl: 'licences/views/template.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      });
  }
]);
