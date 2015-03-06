'use strict';

angular.module('users').factory('Users', ['$window', function ($window) {


  var users = [
    {
      id: 1,
      displayName: 'Stephen Dowsland',
      email: 'admin@domain.com',
      role: 'Administrator'
    },
    {
      id: 2,
      displayName: 'Mark Turner',
      email: 'user@domain.com',
      role: 'Standard User'
    }
  ];

  return {
    all : function() {
      return users;
    },
    one: function(id) {
      return $window._.find(users, function(user) {
        return user.id === id;
      });
    }
  };
}]);
