'use strict';

angular.module('licences').controller('LicencesController', ['$scope', '$state', '$window', '$upload', 'Licences', 'Templates',
  function ($scope, $state, $window, $upload, Licences, Templates) {

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

      var payload = {
        name: $scope.licence.name,
        comment: $scope.licence.comments,
        templateid: $state.params.templateId,
        fieldvalues: []
      };

      angular.forEach($scope.template, function(field){

        payload.fieldvalues.push({
          name: field.name,
          value: field.value
        });
      });

      console.log(payload);

      Licences.save(payload, function(){

      });

      $state.go('create.upload');
    };
  }
]);
