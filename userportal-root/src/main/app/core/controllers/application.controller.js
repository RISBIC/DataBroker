'use strict';

angular.module('core').controller('ApplicationController', ['$log', '$scope', '$state', '$window', 'Global', 'AuthenticationService', 'AUTH_EVENTS', 'Search', 'Listings',
    function($log, $scope, $state, $window, Global, AuthenticationService, AUTH_EVENTS, Search, Listings) {

        $scope.button = function(){

            AuthenticationService.logout();

            $window.sessionStorage.removeItem('userPortalSession');

            $state.go('login');

        };

        $scope.$on(AUTH_EVENTS.loginSuccess, function(){

            $log.info('Login Success: Storing Session');

            $window.sessionStorage.setItem('userPortalSession', JSON.stringify(Global.session));

            $log.info('userPortalSession');
            $log.info(JSON.parse($window.sessionStorage.getItem('userPortalSession')));

            $state.go('home');

        }, true);

        $scope.$on(AUTH_EVENTS.notAuthenticated, function(){

            $log.warn('Not Authenticated Event');

            $state.go('login');
        }, true);

        $scope.loggedin = function() {
            return Global.getSession();
        };

        $scope.isAuthorized = function(roles) {
            return AuthenticationService.isAuthorized(roles);
        };

        $scope.login = function() {
            $state.go('login');
        };

        $scope.logout = function() {
            AuthenticationService.logout();
        };

        $scope.searchString = Search.query;

        $scope.$watch('searchString', function(val) {
            Search.query = val;
        });

        $scope.submitSearch = function($event) {

            if ($event.keyCode === 13) {
                $state.go('listings', {query: $scope.searchString});
            }
        };

      $scope.$on('AUTH_EVENTS.logoutSuccess', function(){
        $scope.searchString = '';

      });


      // Home page logic

      Listings.get({}, function(listings){

        var rootListings = $window._.filter(listings.advertnodes, function(d){
          return d.isRootNode;
        });

        // Check each item have a name - if not use the children
        $window._.each(rootListings, function(d){

          if (d.name === null || d.name === ''){
            if (d.childNodeIds.length) {
              $window._.each(d.childNodeIds, function(child){
                var childNode = $window._.find(listings.advertnodes, function(searchNode){
                  return searchNode.id === child;
                });

                if (childNode.name) {
                  rootListings.push(childNode);
                }
              });
            }
          }

        });

        $scope.rootListings = rootListings;

        // Generate word cloud data

        var tags = [];

        $window._.each(listings.advertnodes, function(d){
          if (d.tags) {
            tags = tags.concat(d.tags);
          }
        });


        if (tags.length > 0) {
          $scope.tags = tags;
        } else {
          $scope.tags = [
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'Newcastle',
            'smn',
            'smn',
            'smn',
            'databroker',
            'databroker',
            'databroker',
            'databroker',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed',
            'speed'
          ];
        }



      });

      $scope.navToSearch = function(term) {
        Search.query = term;
        $state.go('listings');
      }

    }
]);
