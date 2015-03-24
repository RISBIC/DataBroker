'use strict';

angular.module('listings').factory('Listings', ['$resource', 'Global',
  function($resource, Global) {
    return $resource(Global.apiURL + 'adverts/nodes/:advertID', {
      advertID: '@_id'
    });
  }
]);
