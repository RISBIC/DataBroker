'use strict';

angular.module('core').controller('ApplicationController', ['$log', '$scope', '$state',
  function($log, $scope, $state) {

    $scope.welcome = 'Hello World!';

    $scope.button = function(event){
      $log.info('You clicked me');
      $state.go('home');

    };


  }
]);
