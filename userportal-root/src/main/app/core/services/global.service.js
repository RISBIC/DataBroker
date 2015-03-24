'use strict';

angular.module('core').factory('Global', [function () {

  var global = {
    theme: 'default',
    session: null,
    apiURL: 'http://localhost:8080/advert-gateway/ws/',
    setSession: function(session){
      global.session = session;
    },
      getSession: function() {
          return global.session;
      }
  };

  return global;

}]);
