'use strict';

angular.module('listings').controller('ListingsController', ['$scope', '$state', 'Listings', 'Search',
    function($scope, $state, Listings, Search) {

      $scope.searchString = Search.query;

        $scope.$watch('searchString', function(val) {
           Search.query = val;
        });

      /*if ($state.params.query && (!Listings.query || Listings.query === '')) {
        Listings.query = angular.copy($state.params.query);
      }*/




        $scope.listings = Listings.all();

        $scope.$on('AUTH_EVENTS.logoutSuccess', function(){
          $scope.searchString = '';

        });
    }
]).filter('filterListings', function() {
  return function( items, name) {
    var filtered = [];
    if (name) {
      angular.forEach(items, function(item) {
        if(item.name.toLowerCase().indexOf(name.toLowerCase()) != -1) {
          filtered.push(item);
        }
      });
        return filtered;
    } else {
      return items;
    }


  };
});
