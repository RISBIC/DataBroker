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
      state('create', {
        url: '/create',
        templateUrl: 'licences/views/create.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      }).
      state('create.templates', {
        url: '/templates',
        templateUrl: 'licences/views/templates.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      }).
      state('create.template', {
        url: '/templates/:templateId',
        templateUrl: 'licences/views/template.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
      }).
      state('create.upload', {
        url: '/templates/:templateId/upload',
        templateUrl: 'licences/views/upload.view.html',
        controller: 'LicencesController',
        data: {
          authorizedRoles: ['user']
        }
    });
  }
]);
