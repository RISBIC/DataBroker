'use strict';

/**
 * @ngdoc function
 * @name mainApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the mainApp
 */
angular.module('mainApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
