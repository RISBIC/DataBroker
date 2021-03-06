'use strict';

angular.module('users').factory('AuthenticationService', ['$rootScope', '$state', '$log', '$q', '$http', '$window', 'AUTH_EVENTS', 'CONFIG', 'USER_ROLES', 'Global', 'Search', function ($rootScope, $state, $log, $q, $http, $window, AUTH_EVENTS, CONFIG, USER_ROLES, Global, Search) {
    return {
        login: function(email, password){

          return false;

            /*if (email === 'admin@domain.com') {
                return {
                    email: email,
                    name: 'John Doe',
                    roles: ['users']
                };
            } else {
                return {
                    email: email,
                    name: 'John Dee',
                    roles: []
                };
            }*/

            /*var deferred = $q.defer();
             $http({
             method: 'POST',
             url: CONFIG.apiURL + 'account/token',
             headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/json'},
             transformRequest: function(obj) {
             var str = [];
             for(var p in obj){
             str.push(encodeURIComponent(p) + '=' + encodeURIComponent(obj[p]));
             }
             return str.join('&');
             },
             data: {username: username, password: password }
             })
             .success(function(token) {
             $http({
             method: 'GET',
             url: CONFIG.apiURL + 'users/current',
             headers: {
             'Authorization': token.jwt,
             'Accept': 'application/json',
             'Content-Type': 'application/json'
             }
             })
             .success(function(user) {
             user.sessionToken = token.jwt;
             user.roles = user.admin ? [USER_ROLES.admin, USER_ROLES.user] : [USER_ROLES.user];
             deferred.resolve(user);
             })
             .error(function(data, status, headers, config) {
             $log.info(data);
             $log.info(status);
             $log.info(headers);
             $log.info(config);
             });
             })
             .error(function(data, status, headers, config) {
             $log.info(data);
             $log.info(status);
             $log.info(headers);
             $log.info(config);
             });
             return deferred.promise;*/
        },
        logout: function(){

            Global.setSession(null);
            $window.sessionStorage.removeItem('userPortalSession');

            $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);

          Search.query = '';

            $state.go('login');

        },
        register: function(){

        },
        isAuthenticated: function() {

            if(Global.session === null && JSON.parse($window.sessionStorage.getItem('userPortalSession') !== null)){
                Global.setSession(JSON.parse($window.sessionStorage.getItem('userPortalSession')));
            }

            //return !!Global.session;
          return false;
        },
        isAuthorized: function (authorizedRoles) {

            if (!angular.isArray(authorizedRoles)) {

                authorizedRoles = [authorizedRoles];
            }

            return (authorizedRoles.indexOf('public') !== -1  || ( this.isAuthenticated() && $window._.intersection(authorizedRoles, Global.session.roles).length === authorizedRoles.length));
        }
    };
}]);
