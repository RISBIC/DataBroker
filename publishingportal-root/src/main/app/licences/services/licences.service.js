'use strict';

angular.module('licences').factory('Licences', ['$resource', 'CONFIG',
  function($resource, CONFIG) {
    return $resource(CONFIG.apiURL + 'agreements/:agreementId', {
      agreementId: '@_id'
    }, {
      update: {
        method: 'PUT'
      }
    });
  }
]);
