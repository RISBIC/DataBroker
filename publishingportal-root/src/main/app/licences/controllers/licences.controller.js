'use strict';

angular.module('licences').controller('LicencesController', ['$scope', '$state', '$window', '$upload', 'CONFIG', 'Licences', 'Templates',
  function ($scope, $state, $window, $upload, CONFIG, Licences, Templates) {

    $scope.$state = $state;

    var templates = [];

    $scope.addDatePicker = function() {
      $('.inactive-date-picker').datepicker({
        format: 'yyyy/mm/dd',
        autoclose: true
      }).removeClass('inactive-date-picker')
    };

    function retrieveFieldValue(fieldName, fieldString) {
      var theValue = '';

        angular.forEach($scope.licence.sections, function(section, sectionIndex) {

          angular.forEach(section.fieldsdetails, function (field, fieldIndex) {

            if(field.name === $scope.licence[fieldString]) {
              theValue = field.value;
            }

          });
        });

      return theValue;
    }

    $scope.isEndpointAvailableVar = null;

    $scope.isEndpointAvailable = function() {
      if ($scope.isEndpointAvailableVar !== null) return $scope.isEndpointAvailableVar;

      if ($scope.licence === undefined) return false;

      console.log($scope.licence);

      var fieldValue = retrieveFieldValue($scope.licence.endpointfieldname, 'endpointfieldname');

      if (fieldValue) {
        return $scope.isEndpointAvailableVar = fieldValue;
      } else {
        return false;
      }

    };

    $scope.licenceStateVar = null;

    $scope.licenceState = function() {
      if ($scope.licenceStateVar !== null) return $scope.licenceStateVar;

      if ($scope.licence === undefined) return false;

      var fieldValue = retrieveFieldValue($scope.licence.statefieldname, 'statefieldname');

      if (fieldValue) {
        return $scope.licenceStateVar = fieldValue;
      } else {
        return false;
      }

    };

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

        angular.forEach(template.sections, function(section, sectionIndex) {

          angular.forEach(section.fieldsDetails, function (field, fieldIndex) {

            angular.forEach(field.validations, function (validator) {

              var flag = validator.regex.substring(validator.regex.lastIndexOf('/') + 1);

              //String to RegEx wraggle
              validator.regex = validator.regex.replace('/' + flag, '');
              validator.regex = validator.regex.replace('/', '');

              //Convert to native type
              validator.regex = new RegExp(validator.regex, flag);

            });
          });
        });

        $scope.template = template;
      });
    };

    $scope.createLicence = function(){

      var payload = {
        name: $scope.licence.name,
        comment: $scope.licence.comment,
        templateid: $state.params.templateId,
        fieldvalues: []
      };

      angular.forEach($scope.template.sections, function(section){

        angular.forEach(section.fieldsDetails, function(field){

          if(field.type === 'checkbox'){
          field.value = [];

          angular.forEach(field.optionvalues, function(option){

            var state = {};

            state[option.key] = option.value;

            field.value.push(state);

          });
        }

        payload.fieldvalues.push({
          name: field.name,
          value: field.value
        });
      });
    });

      Licences.save(payload, function(response){
        console.log('response');
        console.log(response);
        console.log(response.agreementid);

        $state.go('licences');

      }, function(error){

        console.log('Error');
        console.log(error);

      });
    };

    $scope.updateLicence = function(){

      console.log('Beginning Update...')

      var payload = {
        name: $scope.licence.name,
        comment: $scope.licence.comment,
        templateid: $state.params.templateId,
        fieldvalues: []
      };

      console.log(payload);

      angular.forEach($scope.licence.sections, function(section) {
        angular.forEach(section.fieldsdetails, function (field) {

          if(field.type === 'checkbox'){
            field.value = [];

            angular.forEach(field.optionvalues, function(option){

              var state = {};

              state[option.key] = option.value;

              field.value.push(state);

            });
          }

          console.log(field.name + ': ' + field.value);

          payload.fieldvalues.push({
            name: field.name,
            value: field.value
          });
        });
      });

      console.log(payload);

      Licences.update({agreementId: $state.params.licenceId}, payload);

      $state.go('licences');
    };


    $scope.upload = function (files) {

      var endpoint = '';

      //finds the endpoint property in the field details
      angular.forEach($scope.licence.sections, function(section){

        endpoint = $window._.find(section.fieldsdetails, function(details){

          return details.name === $scope.licence.endpointfieldname

        });

      });

      if (files && files.length) {
        for (var i = 0; i < files.length; i++) {
          var file = files[i];
          $upload.upload({
            url: endpoint.value,
            file: file
          }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
          }).success(function (data, status, headers, config) {
            console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
          });
        }
      }
    };
  }
]);
