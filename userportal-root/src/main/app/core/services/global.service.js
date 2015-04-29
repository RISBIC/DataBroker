'use strict';

angular.module('core').factory('Global', [function () {

  var global = {
    theme: 'default',
    session: null,
//    apiURL: 'http://10.1.20.246/notice-gateway/ws/',
    apiURL: 'http://127.0.0.1/notice-gateway/ws/',
//    apiURL: 'http://publisherportal-arjunatech.rhcloud.com/notice-gateway/ws/',
    setSession: function(session){
      global.session = session;
    },
      getSession: function() {
          return global.session;
      }
  };

  return global;

}]);
