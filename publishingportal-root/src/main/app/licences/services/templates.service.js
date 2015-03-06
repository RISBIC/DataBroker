angular.module('licences').factory('Templates', ['$resource', 'CONFIG',
  function($resource, CONFIG) {
    return $resource(CONFIG.apiURL + 'agreementtemplates/:templateId', {
      templateId: '@_id'
    }, {
      update: {
        method: 'PUT'
      }
    });
  }
]);
