'use strict';

angular.module('licences').controller('LicencesController', ['$scope', '$state', '$window', 'Licences', 'Templates',
  function ($scope, $state, $window, Licences, Templates) {

    var templates = [];

    $scope.getLicences = function(){
      var licences = Licences.get({}, function() {
        $scope.licences = licences['summaries'];
      });
    };

    $scope.getLicence = function () {
      var licence = Licences.get({agreementId: $state.params.licenceId}, function() {
        $scope.licence = licence;
      });
    };

    $scope.getTemplates = function(){
      templates = Templates.get({}, function() {
        $scope.templates = templates['summaries'];
      });
    };

    $scope.getTemplate = function () {
      var template = Templates.get({templateId: $state.params.templateId}, function() {
        $scope.template = template['fieldsdetails'];
        console.log(templates);
      });
    };

    $scope.createLicence = function(){
      console.log($scope.template);
    };
  }
]);
