'use strict';

angular.module('listings').factory('Listings', ['$resource', 'Global',
  function($resource, Global) {
    return $resource(Global.apiURL + 'notices/nodes/:advertID', {
      advertID: '@_id'
    });
  }
]);
