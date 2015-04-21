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




      Listings.get({}, function(listings){
        //console.log(listings.advertnodes);

        /*angular.forEach(listings.advertnodes, function(val, key){
          listings.advertnodes[key].name = null;
        });*/

        $scope.listings = listings.advertnodes;
      });

        $scope.$on('AUTH_EVENTS.logoutSuccess', function(){
          $scope.searchString = '';

        });
    }
]).filter('filterListings', function() {
  return function( items, searchString) {
    var filtered = [];
    if (searchString) {
      angular.forEach(items, function(item) {
        if(item.name && item.name !== null && (item.name.toLowerCase().indexOf(searchString.toLowerCase()) != -1)) {
          filtered.unshift(item);
        } else if(item.owner && item.owner !== null && (item.owner.toLowerCase().indexOf(searchString.toLowerCase()) != -1)) {
          filtered.push(item);
        } else if(item.summary && item.summary !== null && (item.summary.toLowerCase().indexOf(searchString.toLowerCase()) != -1)) {
          filtered.push(item);
        }
      });
        return filtered;
    } else {
      return items;
    }


  };
});
