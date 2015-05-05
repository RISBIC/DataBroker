'use strict';

angular.module('core').factory('Global', [function () {

  var global = {
    theme: 'default',
    session: null,
    apiURL: (location.port === '9000' ? 'http://127.0.0.1' : '') + '/notice-gateway/ws/',
    setSession: function(session){
      global.session = session;
    },
      getSession: function() {
          return global.session;
      }
  };

  return global;

}]);
